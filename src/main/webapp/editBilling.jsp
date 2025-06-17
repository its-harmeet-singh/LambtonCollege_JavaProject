<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <link rel="stylesheet" href="css/style.css">
  <title>Edit Billing</title>
</head>
<body>
<div class="container">
  <h1>Edit Billing Record</h1>
  <form action="billing" method="post">
    <input type="hidden" name="action" value="update">
    <input type="hidden" name="id" value="${bill.id}">
    <label>Patient ID</label><input type="number" name="patientId" value="${bill.patientId}" required>
    <label>Amount</label><input type="number" step="0.01" name="amount" value="${bill.amount}" required>
    <label>Date</label><input type="date" name="billingDate" value="${bill.billingDate}" required>
    <label>Description</label><textarea name="description" rows="2">${bill.description}</textarea>
    <button type="submit">Update</button>
  </form>
</div>
</body>
</html>
