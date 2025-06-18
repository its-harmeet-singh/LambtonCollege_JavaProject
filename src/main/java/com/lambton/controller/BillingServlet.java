package com.lambton.controller;

import com.lambton.dao.BillingDAO;
import com.lambton.dao.PatientDAO;
import com.lambton.model.Billing;
import com.lambton.model.Patient;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet("/billing")
public class BillingServlet extends HttpServlet {
    private BillingDAO dao = new BillingDAO();
    private PatientDAO patientDao = new PatientDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "new":
                // load all patients for dropdown
                List<Patient> patientsForNew = patientDao.getAllPatients();
                req.setAttribute("patients", patientsForNew);
                req.getRequestDispatcher("newBilling.jsp").forward(req, resp);
                break;

            case "edit":
                int id = Integer.parseInt(req.getParameter("id"));
                Billing bill = dao.getBillingById(id);
                req.setAttribute("bill", bill);
                // also need patients for dropdown
                List<Patient> patientsForEdit = patientDao.getAllPatients();
                req.setAttribute("patients", patientsForEdit);
                req.getRequestDispatcher("editBilling.jsp").forward(req, resp);
                break;

            case "delete":
                dao.deleteBilling(Integer.parseInt(req.getParameter("id")));
                resp.sendRedirect("billing");
                break;

            default:  // list
                List<Billing> list = dao.getAllBilling();
                // map patientId -> patientName
                Map<Integer,String> patientMap = patientDao.getAllPatients().stream()
                    .collect(Collectors.toMap(Patient::getId, Patient::getName));
                req.setAttribute("bills", list);
                req.setAttribute("patientMap", patientMap);
                req.getRequestDispatcher("listBilling.jsp").forward(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        int patientId = Integer.parseInt(req.getParameter("patientId"));
        double amount = Double.parseDouble(req.getParameter("amount"));
        LocalDate date = LocalDate.parse(req.getParameter("billingDate"));
        String description = req.getParameter("description");

        if ("insert".equals(action)) {
            dao.insertBilling(new Billing(patientId, amount, date, description));
        } else if ("update".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            dao.updateBilling(new Billing(id, patientId, amount, date, description));
        }

        resp.sendRedirect("billing");
    }
}
