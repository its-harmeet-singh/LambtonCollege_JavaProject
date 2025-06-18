<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8"/>
  <title>Edit Patient</title>
  <link rel="stylesheet" href="css/style.css"/>
</head>
<body>
  <div class="container">
    <h1>Edit Patient</h1>
    <form action="patients" method="post">
      <input type="hidden" name="action" value="update"/>
      <input type="hidden" name="id" value="${patient.id}"/>

      <label for="name">Name</label>
      <input type="text"
             id="name"
             name="name"
             value="${patient.name}"
             required/>

      <label for="age">Age</label>
      <input type="number"
             id="age"
             name="age"
             value="${patient.age}"
             required/>

      <label for="gender">Gender</label>
      <select id="gender" name="gender">
        <option value="Male"   <c:if test="${patient.gender=='Male'}">selected</c:if>>Male</option>
        <option value="Female" <c:if test="${patient.gender=='Female'}">selected</c:if>>Female</option>
        <option value="Other"  <c:if test="${patient.gender=='Other'}">selected</c:if>>Other</option>
      </select>

      <label for="address">Address</label>
      <textarea id="address"
                name="address"
                rows="3"
                required>${patient.address}</textarea>

      <button type="submit">Update</button>
    </form>
  </div>
</body>
</html>
