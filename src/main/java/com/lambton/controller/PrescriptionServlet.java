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

        // must be logged in
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect("login");
            return;
        }

        // lookup appointment
        int apptId = Integer.parseInt(req.getParameter("appointmentId"));
        Appointment appt = apptDao.getAppointmentById(apptId);
        req.setAttribute("appointment", appt);

        // build patientMap exactly like in DoctorHomeServlet
        Map<Integer,String> patientMap = patDao.getAllPatients()
            .stream()
            .collect(Collectors.toMap(Patient::getId, Patient::getName));
        req.setAttribute("patientMap", patientMap);

        // also expose the logged-in doctor if you want to show their name
        req.setAttribute("user", session.getAttribute("user"));

        // now dispatch to the “new” or “edit” JSP
        String action = req.getParameter("action");
        if ("edit".equals(action)) {
            List<Prescription> list = presDao.getByAppointmentId(apptId);
            Prescription p = list.isEmpty() ? new Prescription() : list.get(0);
            req.setAttribute("prescription", p);
            req.getRequestDispatcher("editPrescription.jsp").forward(req,resp);
        } else {
            req.getRequestDispatcher("newPrescription.jsp").forward(req,resp);
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
