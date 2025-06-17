package com.lambton.controller;

import com.lambton.dao.PatientDAO;
import com.lambton.model.Patient;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/patients")
public class PatientServlet extends HttpServlet {
    private PatientDAO dao = new PatientDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "new":
                req.getRequestDispatcher("newPatient.jsp").forward(req, resp);
                break;
            case "edit":
                int id = Integer.parseInt(req.getParameter("id"));
                Patient existing = dao.getPatientById(id);
                req.setAttribute("patient", existing);
                req.getRequestDispatcher("editPatient.jsp").forward(req, resp);
                break;
            case "delete":
                dao.deletePatient(Integer.parseInt(req.getParameter("id")));
                resp.sendRedirect("patients");
                break;
            default:  // list
                List<Patient> list = dao.getAllPatients();
                req.setAttribute("patients", list);
                req.getRequestDispatcher("listPatients.jsp").forward(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        String name = req.getParameter("name");
        int age = Integer.parseInt(req.getParameter("age"));
        String gender = req.getParameter("gender");
        String address = req.getParameter("address");

        if ("insert".equals(action)) {
            dao.insertPatient(new Patient(name, age, gender, address));
        } else if ("update".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            dao.updatePatient(new Patient(id, name, age, gender, address));
        }
        resp.sendRedirect("patients");
    }
}
