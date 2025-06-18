<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"
         isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8"/>
  <title>Appointments</title>
  <link rel="stylesheet" href="css/style.css"/>

  <!-- Quick CSS to fix button layout -->
  <style>
    /* Ensure cells don’t wrap their contents */
    table td {
      white-space: nowrap;
    }

    /* Flex container for the two buttons */
    .action-buttons {
      display: flex;
      justify-content: flex-end;
      gap: 0.5rem;
    }

    /* Tighter padding & smaller font for action buttons */
    .action-buttons a.button {
      display: inline-flex;
      align-items: center;
      justify-content: center;
      padding: 0.4rem 0.8rem;
      font-size: 0.875rem;
    }
  </style>
</head>
<body>
  <div class="container">
    <h1>Appointments</h1>
    <a href="appointments?action=new" class="button">Schedule Appointment</a>

    <table>
      <tr>
        <th>ID</th><th>Patient</th><th>Doctor</th>
        <th>Time</th><th>Reason</th><th>Actions</th>
      </tr>
      <c:forEach var="a" items="${appointments}">
        <tr>
          <td>${a.id}</td>
          <td>${patientMap[a.patientId]}</td>
          <td>${doctorMap[a.doctorId]}</td>
          <td>${a.appointmentTime}</td>
          <td>${a.reason}</td>
          <td>
            <div class="action-buttons">
              <a href="appointments?action=edit&amp;id=${a.id}"
                 class="button">Edit</a>
              <a href="appointments?action=delete&amp;id=${a.id}"
                 class="button"
                 onclick="return confirm('Delete this appointment?')">
                Delete
              </a>
            </div>
          </td>
        </tr>
      </c:forEach>
    </table>
  </div>
</body>
</html>
