<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <link rel="stylesheet" href="css/style.css">
  <title>Edit Appointment</title>
</head>
<body>
<div class="container">
  <h1>Edit Appointment</h1>
  <form action="appointments" method="post">
    <input type="hidden" name="action" value="update">
    <input type="hidden" name="id" value="${appointment.id}">
    <label>Patient ID<input type="number" name="patientId" value="${appointment.patientId}" required></label>
    <label>Doctor ID<input type="number" name="doctorId" value="${appointment.doctorId}" required></label>
    <label>Time<input type="datetime-local" name="appointmentTime"
          value="${appointment.appointmentTime.toString().replace(' ', 'T')}" required></label>
    <label>Reason<textarea name="reason" rows="3">${appointment.reason}</textarea></label>
    <button type="submit">Update</button>
  </form>
</div>
</body>
</html>
