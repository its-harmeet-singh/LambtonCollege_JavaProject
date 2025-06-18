<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"
         isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8"/>
  <title>Edit Appointment</title>
  <link rel="stylesheet" href="css/style.css"/>
</head>
<body>
  <div class="container">
    <h1>Edit Appointment</h1>
    <form action="appointments" method="post">
      <input type="hidden" name="action" value="update"/>
      <input type="hidden" name="id" value="${appointment.id}"/>

      <label for="patientId">Patient</label>
      <select id="patientId" name="patientId" required>
        <c:forEach var="p" items="${patients}">
          <option value="${p.id}"
            <c:if test="${p.id == appointment.patientId}">selected</c:if>>
            ${p.name}
          </option>
        </c:forEach>
      </select>

      <label for="doctorId">Doctor</label>
      <select id="doctorId" name="doctorId" required>
        <c:forEach var="d" items="${doctors}">
          <option value="${d.id}"
            <c:if test="${d.id == appointment.doctorId}">selected</c:if>>
            ${d.name}
          </option>
        </c:forEach>
      </select>

      <label for="appointmentTime">Time</label>
      <input type="datetime-local"
             id="appointmentTime"
             name="appointmentTime"
             value="${appointment.appointmentTime.toString().replace(' ', 'T')}"
             required/>

      <label for="reason">Reason</label>
      <textarea id="reason" name="reason" rows="3">${appointment.reason}</textarea>

      <button type="submit">Update</button>
    </form>
  </div>
</body>
</html>
