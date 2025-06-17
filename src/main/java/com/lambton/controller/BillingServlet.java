package com.lambton.controller;

import com.lambton.dao.BillingDAO;
import com.lambton.model.Billing;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/billing")
public class BillingServlet extends HttpServlet {
    private BillingDAO dao = new BillingDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "new":
                req.getRequestDispatcher("newBilling.jsp").forward(req, resp);
                break;
            case "edit":
                int id = Integer.parseInt(req.getParameter("id"));
                req.setAttribute("bill", dao.getBillingById(id));
                req.getRequestDispatcher("editBilling.jsp").forward(req, resp);
                break;
            case "delete":
                dao.deleteBilling(Integer.parseInt(req.getParameter("id")));
                resp.sendRedirect("billing");
                break;
            default:
                List<Billing> list = dao.getAllBilling();
                req.setAttribute("bills", list);
                req.getRequestDispatcher("listBilling.jsp").forward(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action   = req.getParameter("action");
        int patientId   = Integer.parseInt(req.getParameter("patientId"));
        double amount   = Double.parseDouble(req.getParameter("amount"));
        LocalDate date  = LocalDate.parse(req.getParameter("billingDate"));
        String desc     = req.getParameter("description");

        if ("insert".equals(action)) {
            dao.insertBilling(new Billing(patientId, amount, date, desc));
        } else if ("update".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            dao.updateBilling(new Billing(id, patientId, amount, date, desc));
        }
        resp.sendRedirect("billing");
    }
}
