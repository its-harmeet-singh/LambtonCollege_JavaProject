<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8"/>
  <title>Admin Dashboard</title>
  <link rel="stylesheet" href="css/style.css"/>
</head>
<body>
  <div class="container">
    <h1>🔧 Superuser Dashboard</h1>
    <a href="logout" class="button">Logout</a>
    <ul>
      <li><a href="patients"    class="button">Manage Patients</a></li>
      <li><a href="doctors"     class="button">Manage Doctors</a></li>
      <li><a href="appointments"class="button">Manage Appointments</a></li>
      <li><a href="billing"     class="button">Manage Billing</a></li>
      <!-- add more -->
    </ul>
  </div>
</body>
</html>
