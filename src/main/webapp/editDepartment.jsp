<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <link rel="stylesheet" href="css/style.css">
  <title>Edit Department</title>
</head>
<body>
<div class="container">
  <h1>Edit Department</h1>
  <form action="departments" method="post">
    <input type="hidden" name="action" value="update">
    <input type="hidden" name="id" value="${department.id}">
    <label>Name</label><input type="text" name="name" value="${department.name}" required>
    <label>Location</label><input type="text" name="location" value="${department.location}">
    <button type="submit">Update</button>
  </form>
</div>
</body>
</html>
