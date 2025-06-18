<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"
         isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8"/>
  <title>Edit Department</title>
  <link rel="stylesheet" href="css/style.css"/>
</head>
<body>
  <div class="container">
    <h1>Edit Department</h1>
    <form action="departments" method="post">
      <input type="hidden" name="action" value="update"/>
      <input type="hidden" name="id" value="${department.id}"/>

      <label for="name">Name</label>
      <input type="text"
             id="name"
             name="name"
             value="${department.name}"
             required/>

      <label for="location">Location</label>
      <input type="text"
             id="location"
             name="location"
             value="${department.location}"/>

      <button type="submit">Update</button>
    </form>
  </div>
</body>
</html>
