<%@ page import="java.time.*,
                 java.util.*,
                 com.lambton.dao.AppointmentDAO,
                 com.lambton.model.Appointment,
                 com.lambton.model.Doctor" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
  Doctor doc = (Doctor)session.getAttribute("user");
  AppointmentDAO dao = new AppointmentDAO();
  List<Appointment> all = new ArrayList<>();
  for (Appointment a : dao.getAllAppointments()) {
    if (a.getDoctorId() == doc.getId()) all.add(a);
  }
  LocalDate today = LocalDate.now();
  List<Appointment> todayList   = new ArrayList<>();
  List<Appointment> upcoming    = new ArrayList<>();
  List<Appointment> past        = new ArrayList<>();
  for (Appointment a : all) {
    LocalDate d = a.getAppointmentTime().toLocalDate();
    if (d.isEqual(today))           todayList.add(a);
    else if (d.isAfter(today))      upcoming.add(a);
    else                             past.add(a);
  }
  past.sort(Comparator.comparing(Appointment::getAppointmentTime));
  request.setAttribute("todayList", todayList);
  request.setAttribute("upcoming", upcoming);
  request.setAttribute("past", past);
%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8"/>
  <title>Doctor Home</title>
  <link rel="stylesheet" href="css/style.css"/>
</head>
<body>
  <div class="container">
    <h1>Welcome Dr. ${user.name}</h1>
    <a href="logout" class="button">Logout</a>

    <h2>Today's Appointments</h2>
    <table>
      <tr><th>Time</th><th>Patient ID</th><th>Reason</th></tr>
      <c:forEach var="a" items="${todayList}">
        <tr>
          <td>${a.appointmentTime}</td>
          <td>${a.patientId}</td>
          <td>${a.reason}</td>
        </tr>
      </c:forEach>
    </table>

    <h2>Upcoming</h2>
    <table>
      <tr><th>Date</th><th>Patient ID</th><th>Reason</th></tr>
      <c:forEach var="a" items="${upcoming}">
        <tr>
          <td>${a.appointmentTime}</td>
          <td>${a.patientId}</td>
          <td>${a.reason}</td>
        </tr>
      </c:forEach>
    </table>

    <h2>Past</h2>
    <table>
      <tr><th>Date</th><th>Patient ID</th><th>Reason</th></tr>
      <c:forEach var="a" items="${past}">
        <tr>
          <td>${a.appointmentTime}</td>
          <td>${a.patientId}</td>
          <td>${a.reason}</td>
        </tr>
      </c:forEach>
    </table>
  </div>
</body>
</html>
