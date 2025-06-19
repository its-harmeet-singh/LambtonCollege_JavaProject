package com.lambton.controller;

import com.lambton.dao.AppointmentDAO;
import com.lambton.dao.DiagnosisDAO;
import com.lambton.dao.PatientDAO;
import com.lambton.model.Appointment;
import com.lambton.model.Diagnosis;
import com.lambton.model.Patient;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet("/diagnosis")
public class DiagnosisServlet extends HttpServlet {
    private DiagnosisDAO    diagDao  = new DiagnosisDAO();
    private AppointmentDAO apptDao   = new AppointmentDAO();
    private PatientDAO     patDao    = new PatientDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

        // authentication check
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect("login");
            return;
        }

        // load appointment
        int apptId = Integer.parseInt(req.getParameter("appointmentId"));
        Appointment appt = apptDao.getAppointmentById(apptId);
        req.setAttribute("appointment", appt);

        // build and expose patientMap
        Map<Integer,String> patientMap = patDao.getAllPatients()
            .stream()
            .collect(Collectors.toMap(Patient::getId, Patient::getName));
        req.setAttribute("patientMap", patientMap);

        // expose logged-in doctor
        req.setAttribute("user", session.getAttribute("user"));

        // dispatch to new or edit JSP
        String action = req.getParameter("action");
        if ("edit".equals(action)) {
            List<Diagnosis> list = diagDao.getByAppointmentId(apptId);
            Diagnosis d = list.isEmpty() ? new Diagnosis() : list.get(0);
            req.setAttribute("diagnosis", d);
            req.getRequestDispatcher("editDiagnosis.jsp")
               .forward(req, resp);
        } else {
            req.getRequestDispatcher("newDiagnosis.jsp")
               .forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

        String action = req.getParameter("action");
        int apptId    = Integer.parseInt(req.getParameter("appointmentId"));
        int patId     = Integer.parseInt(req.getParameter("patientId"));
        int docId     = Integer.parseInt(req.getParameter("doctorId"));
        String diag   = req.getParameter("diagnosis");
        LocalDate date = LocalDate.parse(req.getParameter("dateDiagnosed"));
        String notes  = req.getParameter("notes");

        if ("insert".equals(action)) {
            Diagnosis d = new Diagnosis(patId, docId, apptId,
                                        diag, date, notes);
            diagDao.insert(d);
        } else {
            int id = Integer.parseInt(req.getParameter("id"));
            Diagnosis d = new Diagnosis(id, patId, docId, apptId,
                                        diag, date, notes);
            diagDao.update(d);
        }
        resp.sendRedirect("doctorHome");
    }
}
