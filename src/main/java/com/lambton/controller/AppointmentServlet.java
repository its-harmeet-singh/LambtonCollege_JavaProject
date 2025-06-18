package com.lambton.controller;

import com.lambton.dao.AppointmentDAO;
import com.lambton.dao.PatientDAO;
import com.lambton.dao.DoctorDAO;
import com.lambton.model.Appointment;
import com.lambton.model.Patient;
import com.lambton.model.Doctor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet("/appointments")
public class AppointmentServlet extends HttpServlet {
    private AppointmentDAO dao = new AppointmentDAO();
    private PatientDAO patientDao = new PatientDAO();
    private DoctorDAO doctorDao = new DoctorDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "new":
                // load all patients & doctors
                req.setAttribute("patients", patientDao.getAllPatients());
                req.setAttribute("doctors", doctorDao.getAllDoctors());
                req.getRequestDispatcher("newAppointment.jsp").forward(req, resp);
                break;

            case "edit":
                int id = Integer.parseInt(req.getParameter("id"));
                Appointment appt = dao.getAppointmentById(id);
                req.setAttribute("appointment", appt);
                // still need lists for dropdown
                req.setAttribute("patients", patientDao.getAllPatients());
                req.setAttribute("doctors", doctorDao.getAllDoctors());
                req.getRequestDispatcher("editAppointment.jsp").forward(req, resp);
                break;

            case "delete":
                dao.deleteAppointment(Integer.parseInt(req.getParameter("id")));
                resp.sendRedirect("appointments");
                break;

            default: // list
                List<Appointment> list = dao.getAllAppointments();
                // build maps for lookup
                Map<Integer,String> patientMap = patientDao.getAllPatients().stream()
                    .collect(Collectors.toMap(Patient::getId, Patient::getName));
                Map<Integer,String> doctorMap = doctorDao.getAllDoctors().stream()
                    .collect(Collectors.toMap(Doctor::getId, Doctor::getName));
                req.setAttribute("appointments", list);
                req.setAttribute("patientMap", patientMap);
                req.setAttribute("doctorMap", doctorMap);
                req.getRequestDispatcher("listAppointments.jsp").forward(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        int patientId = Integer.parseInt(req.getParameter("patientId"));
        int doctorId = Integer.parseInt(req.getParameter("doctorId"));
        LocalDateTime time = LocalDateTime.parse(req.getParameter("appointmentTime"));
        String reason = req.getParameter("reason");

        if ("insert".equals(action)) {
            dao.insertAppointment(new Appointment(patientId, doctorId, time, reason));
        } else if ("update".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            dao.updateAppointment(new Appointment(id, patientId, doctorId, time, reason));
        }

        // Determine where to send the user next:
        HttpSession session = req.getSession(false);
        String role = (session != null) ? (String) session.getAttribute("role") : null;

        if ("PATIENT".equals(role)) {
            resp.sendRedirect("patientHome");
        } else if ("SUPERUSER".equals(role)) {
            resp.sendRedirect("adminHome.jsp");
        } else if ("DOCTOR".equals(role)) {
            resp.sendRedirect("doctorHome.jsp");
        } else {
            // fallback to appointment list for any other context
            resp.sendRedirect("appointments");
        }
    }

}
