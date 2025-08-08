package com.lambton.controller;

import com.lambton.dao.UserDAO;
import com.lambton.dao.PatientDAO;
import com.lambton.dao.DoctorDAO;
import com.lambton.model.Doctor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserDAO     userDao    = new UserDAO();
    private PatientDAO  patientDao = new PatientDAO();
    private DoctorDAO   doctorDao  = new DoctorDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String email = req.getParameter("email");
        String pass  = req.getParameter("password");

        String role = userDao.loginAndGetRole(email, pass);
        if (role == null) {
            req.setAttribute("error", "Invalid email or password.");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
            return;
        }

        HttpSession session = req.getSession();
        session.setAttribute("role", role);
        session.setAttribute("email", email);

        switch (role) {
            case "SUPERUSER":
                session.setAttribute("role", "ADMIN");
                session.setAttribute("user", email);
                resp.sendRedirect("adminHome.jsp");
                break;
            case "DOCTOR":
                    Doctor doc = doctorDao.getByEmail(email);
                    session.setAttribute("user", doc);
                    session.setAttribute("role", "DOCTOR");
                    session.setAttribute("doctorId", doc.getId());
                    resp.sendRedirect("doctorHome");
                    break;
            case "PATIENT":
                var patient = patientDao.getByEmail(email);
                session.setAttribute("user", patient);
                session.setAttribute("role", "PATIENT");
                session.setAttribute("patientId", patient.getId());
                resp.sendRedirect("patientHome");
                break;
        }
    }
}
