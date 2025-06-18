<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"
         isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8"/>
  <title>Appointment List</title>
  <link rel="stylesheet" href="css/style.css"/>
</head>
<body>
  <div class="container">
    <h1>Appointments</h1>
    <a href="appointments?action=new" class="button">Schedule Appointment</a>
    <table>
      <tr>
        <th>ID</th>
        <th>Patient ID</th>
        <th>Doctor ID</th>
        <th>Time</th>
        <th>Reason</th>
        <th>Actions</th>
      </tr>
      <c:forEach var="a" items="${appointments}">
        <tr>
          <td>${a.id}</td>
          <td>${a.patientId}</td>
          <td>${a.doctorId}</td>
          <td>${a.appointmentTime}</td>
          <td>${a.reason}</td>
          <td>
            <a href="appointments?action=edit&amp;id=${a.id}" class="button">Edit</a>
            <a href="appointments?action=delete&amp;id=${a.id}" class="button"
               onclick="return confirm('Cancel this appointment?')">Delete</a>
          </td>
        </tr>
      </c:forEach>
    </table>
  </div>
</body>
</html>
