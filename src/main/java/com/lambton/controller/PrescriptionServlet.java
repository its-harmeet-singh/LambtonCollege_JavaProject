package com.lambton.controller;

import com.lambton.dao.AppointmentDAO;
import com.lambton.dao.PatientDAO;
import com.lambton.dao.PrescriptionDAO;
import com.lambton.model.Appointment;
import com.lambton.model.Patient;
import com.lambton.model.Prescription;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet("/prescription")
public class PrescriptionServlet extends HttpServlet {
    private PrescriptionDAO presDao   = new PrescriptionDAO();
    private AppointmentDAO  apptDao   = new AppointmentDAO();
    private PatientDAO      patDao    = new PatientDAO();

    @Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

    // Must be logged in
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect("login");
            return;
        }

        String role = String.valueOf(session.getAttribute("role"));
        boolean isDoctor = "DOCTOR".equalsIgnoreCase(role);
        boolean isAdmin  = "ADMIN".equalsIgnoreCase(role) || "SUPERADMIN".equalsIgnoreCase(role);

        // Only allow Doctor or Admin
        if (!(isDoctor || isAdmin)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        // Get and validate appointmentId
        String apptIdStr = req.getParameter("appointmentId");
        if (apptIdStr == null || apptIdStr.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing appointmentId");
            return;
        }
        int apptId;
        try {
            apptId = Integer.parseInt(apptIdStr);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid appointmentId");
            return;
        }

        // Load appointment
        Appointment appt = apptDao.getAppointmentById(apptId);
        if (appt == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Appointment not found");
            return;
        }

        // Resolve doctor context
        Integer sessionDoctorId = (Integer) session.getAttribute("doctorId");
        String doctorName;

        if (isDoctor) {
            // Doctor must have doctorId and must own the appointment
            if (sessionDoctorId == null) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Missing doctorId in session.");
                return;
            }
            if (appt.getDoctorId() != sessionDoctorId) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Not your appointment");
                return;
            }
            // Get doctor name from session user if it's a Doctor object
            Object u = session.getAttribute("user");
            if (u instanceof com.lambton.model.Doctor) {
                doctorName = ((com.lambton.model.Doctor) u).getName();
            } else {
                doctorName = String.valueOf(u);
            }
        } else {
            // Admin path: use the appointment's doctor
            sessionDoctorId = appt.getDoctorId();
            // If you have DoctorDAO available, fetch the name; else show a generic label
            try {
                com.lambton.dao.DoctorDAO doctorDao = new com.lambton.dao.DoctorDAO();
                com.lambton.model.Doctor d = doctorDao.getById(sessionDoctorId);
                doctorName = (d != null) ? d.getName() : "Unknown Doctor";
            } catch (Exception e) {
                doctorName = "Doctor #" + sessionDoctorId;
            }
        }

        // Build patient map (id -> name)
        Map<Integer, String> patientMap = patDao.getAllPatients()
                .stream()
                .collect(Collectors.toMap(Patient::getId, Patient::getName));

        // Load existing prescription (or create empty)
        List<Prescription> list = presDao.getByAppointmentId(apptId);
        Prescription p = list.isEmpty() ? new Prescription() : list.get(0);

        // Expose attributes for JSP
        req.setAttribute("appointment", appt);
        req.setAttribute("patientMap", patientMap);
        req.setAttribute("doctorId", sessionDoctorId);  // hidden field value
        req.setAttribute("doctorName", doctorName);     // display value
        req.setAttribute("prescription", p);

        // Route
        String action = req.getParameter("action");
        if ("new".equalsIgnoreCase(action)) {
            req.getRequestDispatcher("newPrescription.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("editPrescription.jsp").forward(req, resp);
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

        String action    = req.getParameter("action");
        int    apptId    = Integer.parseInt(req.getParameter("appointmentId"));
        int    patId     = Integer.parseInt(req.getParameter("patientId"));
        int    docId     = Integer.parseInt(req.getParameter("doctorId"));
        String med       = req.getParameter("medicine");
        String dosage    = req.getParameter("dosage");
        LocalDate date    = LocalDate.parse(req.getParameter("dateIssued"));
        String instr     = req.getParameter("instructions");

        if ("insert".equals(action)) {
            Prescription p = new Prescription(patId, docId, apptId,
                                              med, dosage, date, instr);
            presDao.insert(p);
        } else {
            int id = Integer.parseInt(req.getParameter("id"));
            Prescription p = new Prescription(id, patId, docId, apptId,
                                              med, dosage, date, instr);
            presDao.update(p);
        }
        resp.sendRedirect("doctorHome");
    }
}
