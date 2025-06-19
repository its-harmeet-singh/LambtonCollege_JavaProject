package com.lambton.controller;

import com.lambton.dao.AppointmentDAO;
import com.lambton.dao.PatientDAO;
import com.lambton.model.Appointment;
import com.lambton.model.Doctor;
import com.lambton.model.Patient;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet("/doctorHome")
public class DoctorHomeServlet extends HttpServlet {
    private AppointmentDAO apptDao   = new AppointmentDAO();
    private PatientDAO     patientDao = new PatientDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect("login");
            return;
        }
        Doctor doc = (Doctor) session.getAttribute("user");

        // Load and filter
        List<Appointment> all = apptDao.getAllAppointments().stream()
            .filter(a -> a.getDoctorId() == doc.getId())
            .collect(Collectors.toList());

        LocalDate today = LocalDate.now();
        List<Appointment> todayList = all.stream()
            .filter(a -> a.getAppointmentTime().toLocalDate().equals(today))
            .collect(Collectors.toList());
        List<Appointment> upcoming = all.stream()
            .filter(a -> a.getAppointmentTime().toLocalDate().isAfter(today))
            .collect(Collectors.toList());
        List<Appointment> past = all.stream()
            .filter(a -> a.getAppointmentTime().toLocalDate().isBefore(today))
            .sorted((a, b) -> a.getAppointmentTime().compareTo(b.getAppointmentTime()))
            .collect(Collectors.toList());

        // Build patient-name lookup
        Map<Integer, String> patientMap = patientDao.getAllPatients()
            .stream()
            .collect(Collectors.toMap(Patient::getId, Patient::getName));

        // Expose to JSP
        req.setAttribute("todayList",  todayList);
        req.setAttribute("upcoming",   upcoming);
        req.setAttribute("past",       past);
        req.setAttribute("patientMap", patientMap);

        req.getRequestDispatcher("doctorHome.jsp")
           .forward(req, resp);
    }
}
