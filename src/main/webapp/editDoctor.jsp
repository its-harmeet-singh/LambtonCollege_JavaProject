<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <link rel="stylesheet" href="css/style.css">
  <title>Edit Doctor</title>
</head>
<body>
<div class="container">
  <h1>Edit Doctor</h1>
  <form action="doctors" method="post">
    <input type="hidden" name="action" value="update">
    <input type="hidden" name="id" value="${doctor.id}">
    <label>Name</label><input type="text" name="name" value="${doctor.name}" required>
    <label>Specialty</label><input type="text" name="specialty" value="${doctor.specialty}">
    <label>Phone</label><input type="text" name="phone" value="${doctor.phone}">
    <label>Email</label><input type="text" name="email" value="${doctor.email}">
    <button type="submit">Update</button>
  </form>
</div>
</body>
</html>
