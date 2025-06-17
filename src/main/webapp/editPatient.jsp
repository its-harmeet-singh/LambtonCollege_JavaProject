<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <link rel="stylesheet" href="css/style.css">
  <title>Edit Patient</title>
</head>
<body>
<div class="container">
  <h1>Edit Patient</h1>
  <form action="patients" method="post">
    <input type="hidden" name="action" value="update">
    <input type="hidden" name="id" value="${patient.id}">

    <label for="name">Name</label>
    <input type="text" id="name" name="name" value="${patient.name}" required>

    <label for="age">Age</label>
    <input type="number" id="age" name="age" value="${patient.age}" required>

    <label for="gender">Gender</label>
    <select id="gender" name="gender">
      <option ${patient.gender=='Male'?'selected':''}>Male</option>
      <option ${patient.gender=='Female'?'selected':''}>Female</option>
      <option ${patient.gender=='Other'?'selected':''}>Other</option>
    </select>

    <label for="address">Address</label>
    <textarea id="address" name="address" rows="3">${patient.address}</textarea>

    <button type="submit">Update</button>
  </form>
</div>
</body>
</html>
