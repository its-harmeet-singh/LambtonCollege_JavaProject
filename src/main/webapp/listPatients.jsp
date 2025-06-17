<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <link rel="stylesheet" href="css/style.css">
  <title>Patient List</title>
</head>
<body>
<div class="container">
  <h1>Patients</h1>
  <a href="patients?action=new" class="button">Add New Patient</a>
  <table>
    <tr><th>ID</th><th>Name</th><th>Age</th><th>Gender</th><th>Address</th><th>Actions</th></tr>
    <c:forEach var="p" items="${patients}">
      <tr>
        <td>${p.id}</td>
        <td>${p.name}</td>
        <td>${p.age}</td>
        <td>${p.gender}</td>
        <td>${p.address}</td>
        <td>
          <a href="patients?action=edit&id=${p.id}" class="button">Edit</a>
          <a href="patients?action=delete&id=${p.id}" class="button"
             onclick="return confirm('Delete this patient?')">Delete</a>
        </td>
      </tr>
    </c:forEach>
  </table>
</div>
</body>
</html>
