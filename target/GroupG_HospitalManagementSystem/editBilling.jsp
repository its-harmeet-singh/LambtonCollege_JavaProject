<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"
         isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8"/>
  <title>Edit Billing Record</title>
  <link rel="stylesheet" href="css/style.css"/>
</head>
<body>
  <div class="container">
    <h1>Edit Billing Record</h1>
    <form action="billing" method="post">
      <input type="hidden" name="action" value="update"/>
      <input type="hidden" name="id" value="${bill.id}"/>

      <label for="patientId">Patient ID</label>
      <input type="number"
             id="patientId"
             name="patientId"
             value="${bill.patientId}"
             required/>

      <label for="amount">Amount</label>
      <input type="number"
             step="0.01"
             id="amount"
             name="amount"
             value="${bill.amount}"
             required/>

      <label for="billingDate">Date</label>
      <input type="date"
             id="billingDate"
             name="billingDate"
             value="${bill.billingDate}"
             required/>

      <label for="description">Description</label>
      <textarea id="description"
                name="description"
                rows="3">${bill.description}</textarea>

      <button type="submit">Update</button>
    </form>
  </div>
</body>
</html>
