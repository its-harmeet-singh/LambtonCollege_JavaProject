package com.lambton.controller;

import com.lambton.dao.BillingDAO;
import com.lambton.model.Billing;
import com.lambton.model.Patient;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/showBilling")
public class ShowBillingServlet extends HttpServlet {
    private BillingDAO billDao = new BillingDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // 1) get patient from session
        HttpSession session = req.getSession(false);
        Patient pat = (Patient) session.getAttribute("user");

        // 2) load all bills for this patient
        List<Billing> bills = billDao.getByPatientId(pat.getId());
        req.setAttribute("bills", bills);

        // 3) forward to JSP
        req.getRequestDispatcher("showBilling.jsp").forward(req, resp);
    }
}
