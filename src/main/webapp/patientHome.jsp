<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"
         isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    <!-- <a href="appointments?action=new" class="button">Schedule Appointment</a> -->
    <a href="patientBookAppointment" class="button">Book Appointment</a>

    <!-- Upcoming Appointments -->
    <h2>Upcoming Appointments</h2>
    <table>
      <tr>
        <th>Date &amp; Time</th><th>Doctor</th><th>Reason</th><th>Actions</th>
      </tr>
      <c:forEach var="a" items="${upcomingA}">
        <tr>
          <td>${timeMap[a.id]}</td>
          <td>${doctorMap[a.doctorId]}</td>
          <td>${a.reason}</td>
          <td>
            <a href="appointments?action=edit&amp;id=${a.id}" class="button">Edit</a>
          </td>
        </tr>
      </c:forEach>
    </table>

    <!-- Past Appointments -->
    <h2>Past Appointments</h2>
    <table>
      <tr>
        <th>Date &amp; Time</th><th>Doctor</th><th>Reason</th>
        <th>Rx &amp; Dx</th><th>Bill</th>
      </tr>
      <c:forEach var="a" items="${pastA}">
        <tr>
          <td>${timeMap[a.id]}</td>
          <td>${doctorMap[a.doctorId]}</td>
          <td>${a.reason}</td>
          <td>
            <a href="showPrescription?appointmentId=${a.id}" class="button">
              View Rx & Dx
            </a>
          </td>
          <td>
            <a href="showBilling" class="button">
              View Bill
            </a>
          </td>
        </tr>
      </c:forEach>
    </table>
  </div>
</body>
</html>
