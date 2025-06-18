<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"
         isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8"/>
  <title>Your Bills</title>
  <link rel="stylesheet" href="css/style.css"/>
</head>
<body>
  <div class="container">
    <h1>Your Bills</h1>
    <a href="patientHome" class="button">Back to Home</a>
    <table>
      <tr>
        <th>Amount</th><th>Date</th><th>Description</th>
      </tr>
      <c:forEach var="b" items="${bills}">
        <tr>
          <td>${b.amount}</td>
          <td>${b.billingDate}</td>
          <td>${b.description}</td>
        </tr>
      </c:forEach>
    </table>
  </div>
</body>
</html>
