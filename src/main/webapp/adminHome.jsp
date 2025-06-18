<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8"/>
  <title>Superuser Dashboard</title>
  <link rel="stylesheet" href="css/style.css"/>
  <style>
    .admin-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
    }
    .admin-actions {
      display: flex;
      flex-wrap: wrap;
      gap: 1rem;
      margin-top: 2rem;
    }
    .admin-actions .button {
      flex: 1 1 200px;
      text-align: center;
    }
  </style>
</head>
<body>
  <div class="container">
    <div class="admin-header">
      <h1>🔧 Superuser Dashboard</h1>
      <a href="logout" class="button">Logout</a>
    </div>

    <div class="admin-actions">
      <a href="patients"     class="button">Manage Patients</a>
      <a href="doctors"      class="button">Manage Doctors</a>
      <a href="appointments" class="button">Manage Appointments</a>
      <a href="billing"      class="button">Manage Billing</a>
    </div>
  </div>
</body>
</html>
