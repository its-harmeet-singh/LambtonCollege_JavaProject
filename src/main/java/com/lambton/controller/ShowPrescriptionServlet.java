package com.lambton.controller;

import com.lambton.dao.PrescriptionDAO;
import com.lambton.dao.DiagnosisDAO;
import com.lambton.model.Prescription;
import com.lambton.model.Diagnosis;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/showPrescription")
public class ShowPrescriptionServlet extends HttpServlet {
    private PrescriptionDAO presDao = new PrescriptionDAO();
    private DiagnosisDAO    diagDao = new DiagnosisDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String strId = req.getParameter("appointmentId");
        if (strId == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing appointmentId");
            return;
        }
        int apptId = Integer.parseInt(strId);

        List<Prescription> scripts = presDao.getByAppointmentId(apptId);
        List<Diagnosis>    diags   = diagDao.getByAppointmentId(apptId);

        req.setAttribute("scripts", scripts);
        req.setAttribute("diags",   diags);
        req.getRequestDispatcher("showPrescription.jsp")
           .forward(req, resp);
    }
}
