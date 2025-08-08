package com.lambton.controller;

import com.lambton.dao.AppointmentDAO;
import com.lambton.dao.DiagnosisDAO;
import com.lambton.dao.PatientDAO;
import com.lambton.model.Appointment;
import com.lambton.model.Diagnosis;
import com.lambton.model.Patient;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet("/diagnosis")
public class DiagnosisServlet extends HttpServlet {
    private DiagnosisDAO    diagDao  = new DiagnosisDAO();
    private AppointmentDAO apptDao   = new AppointmentDAO();
    private PatientDAO     patDao    = new PatientDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect("login");
            return;
        }

        String role = String.valueOf(session.getAttribute("role"));
        boolean isDoctor = "DOCTOR".equalsIgnoreCase(role);
        boolean isAdmin  = "ADMIN".equalsIgnoreCase(role) || "SUPERADMIN".equalsIgnoreCase(role);

        if (!(isDoctor || isAdmin)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        // appointmentId param
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

        // Resolve doctorId + doctorName
        Integer doctorId = (Integer) session.getAttribute("doctorId");
        String doctorName;

        if (isDoctor) {
            if (doctorId == null) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Missing doctorId in session.");
                return;
            }
            if (appt.getDoctorId() != doctorId) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Not your appointment");
                return;
            }
            Object u = session.getAttribute("user");
            doctorName = (u instanceof com.lambton.model.Doctor)
                    ? ((com.lambton.model.Doctor) u).getName()
                    : String.valueOf(u);
        } else {
            // Admin path: use appointment's doctor
            doctorId = appt.getDoctorId();
            com.lambton.dao.DoctorDAO doctorDao = new com.lambton.dao.DoctorDAO();
            com.lambton.model.Doctor d = doctorDao.getById(doctorId);
            doctorName = (d != null) ? d.getName() : ("Doctor #" + doctorId);
        }

        // Patients map (id -> name)
        Map<Integer, String> patientMap = patDao.getAllPatients()
                .stream()
                .collect(Collectors.toMap(Patient::getId, Patient::getName));

        // Existing diagnosis (or empty)
        List<Diagnosis> list = diagDao.getByAppointmentId(apptId);
        Diagnosis d = list.isEmpty() ? new Diagnosis() : list.get(0);

        // Attributes for JSP
        req.setAttribute("appointment", appt);
        req.setAttribute("patientMap", patientMap);
        req.setAttribute("doctorId", doctorId);
        req.setAttribute("doctorName", doctorName);
        req.setAttribute("diagnosis", d);

        // Route
        String action = req.getParameter("action");
        if ("new".equalsIgnoreCase(action)) {
            req.getRequestDispatcher("newDiagnosis.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("editDiagnosis.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

        String action = req.getParameter("action");
        int apptId    = Integer.parseInt(req.getParameter("appointmentId"));
        int patId     = Integer.parseInt(req.getParameter("patientId"));
        int docId     = Integer.parseInt(req.getParameter("doctorId"));
        String diag   = req.getParameter("diagnosis");
        LocalDate date = LocalDate.parse(req.getParameter("dateDiagnosed"));
        String notes  = req.getParameter("notes");

        if ("insert".equals(action)) {
            Diagnosis d = new Diagnosis(patId, docId, apptId,
                                        diag, date, notes);
            diagDao.insert(d);
        } else {
            int id = Integer.parseInt(req.getParameter("id"));
            Diagnosis d = new Diagnosis(id, patId, docId, apptId,
                                        diag, date, notes);
            diagDao.update(d);
        }
        resp.sendRedirect("doctorHome");
    }
}
