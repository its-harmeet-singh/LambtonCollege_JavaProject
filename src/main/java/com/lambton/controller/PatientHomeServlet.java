package com.lambton.controller;

import com.lambton.dao.AppointmentDAO;
import com.lambton.dao.BillingDAO;
import com.lambton.dao.DoctorDAO;
import com.lambton.dao.PrescriptionDAO;
import com.lambton.model.Appointment;
import com.lambton.model.Billing;
import com.lambton.model.Prescription;
import com.lambton.model.Patient;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet("/patientHome")
public class PatientHomeServlet extends HttpServlet {
    private PrescriptionDAO presDao  = new PrescriptionDAO();
    private AppointmentDAO  apptDao  = new AppointmentDAO();
    private BillingDAO      billDao  = new BillingDAO();
    private DoctorDAO       docDao   = new DoctorDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect("login");
            return;
        }
        Patient pat = (Patient) session.getAttribute("user");

        // 1) Prescriptions
        List<Prescription> scripts = presDao.getByPatientId(pat.getId());
        req.setAttribute("scripts", scripts);

        // 2) Appointments split & doctor‐name map
        List<Appointment> allA = apptDao.getAllAppointments().stream()
            .filter(a -> a.getPatientId() == pat.getId())
            .collect(Collectors.toList());
        LocalDate today = LocalDate.now();
        List<Appointment> upcomingA = allA.stream()
            .filter(a -> a.getAppointmentTime().toLocalDate().isAfter(today))
            .collect(Collectors.toList());
        List<Appointment> pastA = allA.stream()
            .filter(a -> a.getAppointmentTime().toLocalDate().isBefore(today))
            .sorted((a,b)->a.getAppointmentTime().compareTo(b.getAppointmentTime()))
            .collect(Collectors.toList());
        req.setAttribute("upcomingA", upcomingA);
        req.setAttribute("pastA", pastA);

        Map<Integer,String> doctorMap = docDao.getAllDoctors().stream()
            .collect(Collectors.toMap(d->d.getId(), d->d.getName()));
        req.setAttribute("doctorMap", doctorMap);

        // 3) Bills for this patient
        List<Billing> bills = billDao.getByPatientId(pat.getId());
        req.setAttribute("bills", bills);

        // 4) Dispatch to JSP
        req.getRequestDispatcher("patientHome.jsp").forward(req, resp);
    }
}
