package com.lambton.controller;

import com.lambton.dao.DoctorDAO;
import com.lambton.model.Doctor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/doctors")
public class DoctorServlet extends HttpServlet {
    private DoctorDAO dao = new DoctorDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "new":
                req.getRequestDispatcher("newDoctor.jsp").forward(req, resp);
                break;
            case "edit":
                int id = Integer.parseInt(req.getParameter("id"));
                req.setAttribute("doctor", dao.getDoctorById(id));
                req.getRequestDispatcher("editDoctor.jsp").forward(req, resp);
                break;
            case "delete":
                dao.deleteDoctor(Integer.parseInt(req.getParameter("id")));
                resp.sendRedirect("doctors");
                break;
            default:
                List<Doctor> list = dao.getAllDoctors();
                req.setAttribute("doctors", list);
                req.getRequestDispatcher("listDoctors.jsp").forward(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        String name      = req.getParameter("name");
        String specialty = req.getParameter("specialty");
        String phone     = req.getParameter("phone");
        String email     = req.getParameter("email");

        if ("insert".equals(action)) {
            dao.insertDoctor(new Doctor(name, specialty, phone, email));
        } else if ("update".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            dao.updateDoctor(new Doctor(id, name, specialty, phone, email));
        }
        resp.sendRedirect("doctors");
    }
}
