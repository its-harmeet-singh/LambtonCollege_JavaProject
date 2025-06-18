<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"
         isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8"/>
  <title>Edit Doctor</title>
  <link rel="stylesheet" href="css/style.css"/>
</head>
<body>
  <div class="container">
    <h1>Edit Doctor</h1>
    <form action="doctors" method="post">
      <input type="hidden" name="action" value="update"/>
      <input type="hidden" name="id" value="${doctor.id}"/>

      <label for="name">Name</label>
      <input type="text"
             id="name"
             name="name"
             value="${doctor.name}"
             required/>

      <label for="specialty">Specialty</label>
      <input type="text"
             id="specialty"
             name="specialty"
             value="${doctor.specialty}"/>

      <label for="phone">Phone</label>
      <input type="text"
             id="phone"
             name="phone"
             value="${doctor.phone}"/>

      <label for="email">Email</label>
      <input type="email"
             id="email"
             name="email"
             value="${doctor.email}"/>

      <button type="submit">Update</button>
    </form>
  </div>
</body>
</html>
