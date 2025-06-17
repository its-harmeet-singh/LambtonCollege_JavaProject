<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <link rel="stylesheet" href="css/style.css">
  <title>Doctor List</title>
</head>
<body>
<div class="container">
  <h1>Doctors</h1>
  <a href="doctors?action=new" class="button">Add Doctor</a>
  <table>
    <tr><th>ID</th><th>Name</th><th>Specialty</th><th>Phone</th><th>Email</th><th>Actions</th></tr>
    <c:forEach var="d" items="${doctors}">
      <tr>
        <td>${d.id}</td>
        <td>${d.name}</td>
        <td>${d.specialty}</td>
        <td>${d.phone}</td>
        <td>${d.email}</td>
        <td>
          <a href="doctors?action=edit&id=${d.id}" class="button">Edit</a>
          <a href="doctors?action=delete&id=${d.id}" class="button"
             onclick="return confirm('Delete this doctor?')">Delete</a>
        </td>
      </tr>
    </c:forEach>
  </table>
</div>
</body>
</html>
