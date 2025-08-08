<%@ page contentType="text/html; charset=UTF-8"
         isELIgnored="false" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html><head>
  <meta charset="UTF-8"/>
  <title>Doctor Home</title>
  <link rel="stylesheet" href="css/style.css"/>
  <style>
    table th, table td { white-space: nowrap; }
    .action-buttons { display:flex; gap:.5rem; justify-content:flex-end; }
    .button { padding:.5rem 1rem; }
  </style>
</head><body>
  <div class="container">
    <div style="display:flex;justify-content:space-between;align-items:center;">
      <h1>Welcome Dr. ${user.name}</h1>
      <a href="logout" class="button">Logout</a>
    </div>

    <!-- Today's Appointments -->
    <h2>Today's Appointments</h2>
    <table>
      <tr>
        <th>Time</th><th>Patient</th><th>Reason</th><th>Actions</th>
      </tr>
      <c:forEach var="a" items="${todayList}">
        <tr>
          <td>${a.appointmentTime}</td>
          <td>${patientMap[a.patientId]}</td>
          <td>${a.reason}</td>
          <td>
            <div class="action-buttons">
              <a href="prescription?action=new&amp;appointmentId=${a.id}"
                 class="button">Add Rx</a>
              <a href="diagnosis?action=new&amp;appointmentId=${a.id}"
                 class="button">Add Dx</a>
            </div>
          </td>
        </tr>
      </c:forEach>
      <c:if test="${empty todayList}">
        <tr><td colspan="4" style="text-align:center">No appointments today.</td></tr>
      </c:if>
    </table>

    <!-- Upcoming omitted… -->

    <!-- Past Appointments -->
    <h2>Past Appointments</h2>
    <table>
      <tr>
        <th>Date &amp; Time</th><th>Patient</th><th>Reason</th><th>Actions</th>
      </tr>
      <c:forEach var="a" items="${past}">
        <tr>
          <td>${a.appointmentTime}</td>
          <td>${patientMap[a.patientId]}</td>
          <td>${a.reason}</td>
          <td>
            <div class="action-buttons">
              <a href="prescription?action=edit&amp;appointmentId=${a.id}"
                 class="button">Edit Rx</a>
              <a href="diagnosis?action=edit&amp;appointmentId=${a.id}"
                 class="button">Edit Dx</a>
              <a href="prescription?action=view&amp;appointmentId=${a.id}"
                 class="button">View Rx</a>
            </div>
          </td>
        </tr>
      </c:forEach>
      <c:if test="${empty past}">
        <tr><td colspan="4" style="text-align:center">No past appointments.</td></tr>
      </c:if>
    </table>
  </div>
</body></html>
