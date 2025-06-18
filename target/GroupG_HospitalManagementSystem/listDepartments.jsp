<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"
         isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8"/>
  <title>Department List</title>
  <link rel="stylesheet" href="css/style.css"/>
</head>
<body>
  <div class="container">
    <h1>Departments</h1>
    <a href="departments?action=new" class="button">Add Department</a>
    <table>
      <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Location</th>
        <th>Actions</th>
      </tr>
      <c:forEach var="d" items="${departments}">
        <tr>
          <td>${d.id}</td>
          <td>${d.name}</td>
          <td>${d.location}</td>
          <td>
            <a href="departments?action=edit&amp;id=${d.id}" class="button">Edit</a>
            <a href="departments?action=delete&amp;id=${d.id}" class="button"
               onclick="return confirm('Delete this department?')">Delete</a>
          </td>
        </tr>
      </c:forEach>
    </table>
  </div>
</body>
</html>
