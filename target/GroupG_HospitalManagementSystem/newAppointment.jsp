<html>
<head>
  <link rel="stylesheet" href="css/style.css">
  <title>New Appointment</title>
</head>
<body>
<div class="container">
  <h1>Schedule New Appointment</h1>
  <form action="appointments" method="post">
    <input type="hidden" name="action" value="insert">
    <label>Patient ID<input type="number" name="patientId" required></label>
    <label>Doctor ID<input type="number" name="doctorId" required></label>
    <label>Time<input type="datetime-local" name="appointmentTime" required></label>
    <label>Reason<textarea name="reason" rows="3"></textarea></label>
    <button type="submit">Save</button>
  </form>
</div>
</body>
</html>
