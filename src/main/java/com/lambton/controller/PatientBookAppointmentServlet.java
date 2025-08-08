package com.lambton.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lambton.dao.AppointmentDAO;
import com.lambton.dao.DoctorDAO;

@WebServlet("/patientBookAppointment")
public class PatientBookAppointmentServlet extends HttpServlet {
    private AppointmentDAO appointmentDAO;
    private DoctorDAO doctorDAO;

    @Override
    public void init() {
        appointmentDAO = new AppointmentDAO();
        doctorDAO = new DoctorDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("patientId") == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        req.setAttribute("doctors", doctorDAO.findAllActive());
        req.getRequestDispatcher("patientAppointmentForm.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("patientId") == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        String doctorIdStr = req.getParameter("doctorId");
        String dateStr     = req.getParameter("appointmentDate"); 
        String timeStr     = req.getParameter("appointmentTime");
        String reason      = req.getParameter("reason");

        if (doctorIdStr == null || doctorIdStr.isBlank()
            || dateStr == null || dateStr.isBlank()
            || timeStr == null || timeStr.isBlank()) {
            req.setAttribute("error", "Please choose a doctor, date and time.");
            req.setAttribute("doctors", doctorDAO.findAllActive());
            req.getRequestDispatcher("patientAppointmentForm.jsp").forward(req, resp);
            return;
        }

        try {
            int patientId = (int) session.getAttribute("patientId");
            int doctorId  = Integer.parseInt(doctorIdStr);

            java.time.LocalDate date = java.time.LocalDate.parse(dateStr);
            java.time.LocalTime time = java.time.LocalTime.parse(timeStr);

            appointmentDAO.insertForPatient(patientId, doctorId, date, time, reason);
            resp.sendRedirect("patientHome?success=1");
        } catch (Exception e) {
            req.setAttribute("error", "Booking failed: " + e.getMessage());
            req.setAttribute("doctors", doctorDAO.findAllActive());
            req.getRequestDispatcher("patientAppointmentForm.jsp").forward(req, resp);
        }
    }
}
