package com.lambton.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lambton.dao.PatientDAO;
import com.lambton.model.PatientSearchRow;

@WebServlet("/patientSearch")
public class PatientSearchServlet extends HttpServlet {

    private final PatientDAO patientDAO = new PatientDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("patientSearch.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String role = (String) session.getAttribute("role"); 
        Integer doctorId = (Integer) session.getAttribute("doctorId"); 

        String name = req.getParameter("name");
        String dateStr = req.getParameter("date"); 
        LocalDate date = (dateStr == null || dateStr.isBlank()) ? null : LocalDate.parse(dateStr);

        boolean isAdmin = "ADMIN".equalsIgnoreCase(role) || "SUPERADMIN".equalsIgnoreCase(role);
        boolean isDoctor = "DOCTOR".equalsIgnoreCase(role);

        if (!isAdmin && !isDoctor) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        Integer effectiveDoctorId = isDoctor ? doctorId : null;

        List<PatientSearchRow> results = patientDAO.searchPatients(name, date, effectiveDoctorId);

        req.setAttribute("results", results);
        req.setAttribute("name", name);
        req.setAttribute("date", dateStr);
        req.setAttribute("isAdmin", isAdmin);
        req.getRequestDispatcher("patientSearch.jsp").forward(req, resp);
    }
}
