<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"
         isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8"/>
  <title>Billing Records</title>
  <link rel="stylesheet" href="css/style.css"/>
</head>
<body>
  <div class="container">
    <h1>Billing</h1>
    <a href="billing?action=new" class="button">Add Bill</a>
    <table>
      <tr>
        <th>ID</th>
        <th>Patient ID</th>
        <th>Amount</th>
        <th>Date</th>
        <th>Description</th>
        <th>Actions</th>
      </tr>
      <c:forEach var="b" items="${bills}">
        <tr>
          <td>${b.id}</td>
          <td>${b.patientId}</td>
          <td>${b.amount}</td>
          <td>${b.billingDate}</td>
          <td>${b.description}</td>
          <td>
            <a href="billing?action=edit&amp;id=${b.id}" class="button">Edit</a>
            <a href="billing?action=delete&amp;id=${b.id}" class="button"
               onclick="return confirm('Delete this bill?')">Delete</a>
          </td>
        </tr>
      </c:forEach>
    </table>
  </div>
</body>
</html>
