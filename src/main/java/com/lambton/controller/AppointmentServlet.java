package com.lambton.controller;

import com.lambton.dao.AppointmentDAO;
import com.lambton.model.Appointment;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/appointments")
public class AppointmentServlet extends HttpServlet {
    private AppointmentDAO dao = new AppointmentDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "new":
                req.getRequestDispatcher("newAppointment.jsp").forward(req, resp);
                break;
            case "edit":
                int id = Integer.parseInt(req.getParameter("id"));
                req.setAttribute("appointment", dao.getAppointmentById(id));
                req.getRequestDispatcher("editAppointment.jsp").forward(req, resp);
                break;
            case "delete":
                dao.deleteAppointment(Integer.parseInt(req.getParameter("id")));
                resp.sendRedirect("appointments");
                break;
            default:
                List<Appointment> list = dao.getAllAppointments();
                req.setAttribute("appointments", list);
                req.getRequestDispatcher("listAppointments.jsp").forward(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        int patientId = Integer.parseInt(req.getParameter("patientId"));
        int doctorId  = Integer.parseInt(req.getParameter("doctorId"));
        LocalDateTime time = LocalDateTime.parse(req.getParameter("appointmentTime"));
        String reason = req.getParameter("reason");

        if ("insert".equals(action)) {
            dao.insertAppointment(new Appointment(patientId, doctorId, time, reason));
        } else if ("update".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            dao.updateAppointment(new Appointment(id, patientId, doctorId, time, reason));
        }
        resp.sendRedirect("appointments");
    }
}
