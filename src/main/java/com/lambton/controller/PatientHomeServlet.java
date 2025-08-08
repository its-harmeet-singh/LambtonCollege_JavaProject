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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.time.format.DateTimeFormatter;

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

        // 2) Appointments (split upcoming/past by timestamp)
        List<Appointment> allA = apptDao.getAllAppointments().stream()
            .filter(a -> a.getPatientId() == pat.getId())
            .collect(Collectors.toList());

        java.time.LocalDateTime now = java.time.LocalDateTime.now();

        List<Appointment> upcomingA = allA.stream()
            .filter(a -> a.getAppointmentTime().isAfter(now))
            .sorted(java.util.Comparator.comparing(Appointment::getAppointmentTime)) // soonest first
            .collect(Collectors.toList());

        List<Appointment> pastA = allA.stream()
            .filter(a -> a.getAppointmentTime().isBefore(now))
            .sorted(java.util.Comparator.comparing(Appointment::getAppointmentTime).reversed()) // latest first
            .collect(Collectors.toList());
        
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        // Build a map: appointmentId -> "yyyy-MM-dd HH:mm"
        Map<Integer, String> timeMap = allA.stream()
            .collect(Collectors.toMap(Appointment::getId,
                    a -> a.getAppointmentTime().format(fmt)));

        req.setAttribute("timeMap", timeMap);
        req.setAttribute("upcomingA", upcomingA);
        req.setAttribute("pastA", pastA);

        // 3) Doctor name map
        Map<Integer, String> doctorMap = docDao.getAllDoctors().stream()
            .collect(Collectors.toMap(d -> d.getId(), d -> d.getName()));
        req.setAttribute("doctorMap", doctorMap);

        // 4) Bills
        List<Billing> bills = billDao.getByPatientId(pat.getId());
        req.setAttribute("bills", bills);

        // 5) View
        req.getRequestDispatcher("patientHome.jsp").forward(req, resp);
    }

}
