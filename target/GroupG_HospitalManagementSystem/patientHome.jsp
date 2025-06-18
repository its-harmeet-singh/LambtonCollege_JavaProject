<%@ page import="java.time.*,
                 java.util.*,
                 com.lambton.dao.AppointmentDAO,
                 com.lambton.dao.PrescriptionDAO,
                 com.lambton.model.Appointment,
                 com.lambton.model.Prescription,
                 com.lambton.model.Patient" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
  Patient pat = (Patient)session.getAttribute("user");

  // appointments
  AppointmentDAO aDao = new AppointmentDAO();
  List<Appointment> allA = new ArrayList<>();
  for (Appointment a : aDao.getAllAppointments()) {
    if (a.getPatientId() == pat.getId()) allA.add(a);
  }
  LocalDate today = LocalDate.now();
  List<Appointment> upcomingA = new ArrayList<>(), pastA = new ArrayList<>();
  for (Appointment a : allA) {
    LocalDate d = a.getAppointmentTime().toLocalDate();
    if (d.isAfter(today)) upcomingA.add(a);
    else if (d.isBefore(today)) pastA.add(a);
  }
  upcomingA.sort(Comparator.comparing(Appointment::getAppointmentTime));
  pastA.sort(Comparator.comparing(Appointment::getAppointmentTime));
  request.setAttribute("upcomingA", upcomingA);
  request.setAttribute("pastA", pastA);

  // prescriptions
  PrescriptionDAO pDao = new PrescriptionDAO();
  List<Prescription> scripts = pDao.getByPatientId(pat.getId());
  request.setAttribute("scripts", scripts);
%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8"/>
  <title>Patient Home</title>
  <link rel="stylesheet" href="css/style.css"/>
</head>
<body>
  <div class="container">
    <h1>Welcome ${user.name}</h1>
    <a href="logout" class="button">Logout</a>

    <h2>Your Prescriptions</h2>
    <table>
      <tr><th>Date</th><th>Medication</th><th>Dosage</th></tr>
      <c:forEach var="p" items="${scripts}">
        <tr>
          <td>${p.dateIssued}</td>
          <td>${p.medicine}</td>
          <td>${p.dosage}</td>
        </tr>
      </c:forEach>
    </table>

    <h2>Upcoming Appointments</h2>
    <table>
      <tr><th>Date</th><th>Doctor ID</th><th>Reason</th></tr>
      <c:forEach var="a" items="${upcomingA}">
        <tr>
          <td>${a.appointmentTime}</td>
          <td>${a.doctorId}</td>
          <td>${a.reason}</td>
        </tr>
      </c:forEach>
    </table>

    <h2>Past Appointments</h2>
    <table>
      <tr><th>Date</th><th>Doctor ID</th><th>Reason</th></tr>
      <c:forEach var="a" items="${pastA}">
        <tr>
          <td>${a.appointmentTime}</td>
          <td>${a.doctorId}</td>
          <td>${a.reason}</td>
        </tr>
      </c:forEach>
    </table>
  </div>
</body>
</html>
