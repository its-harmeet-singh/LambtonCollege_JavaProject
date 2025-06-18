<html>
<head>
  <link rel="stylesheet" href="css/style.css">
  <title>Add Billing</title>
</head>
<body>
<div class="container">
  <h1>Create Billing Record</h1>
  <form action="billing" method="post">
    <input type="hidden" name="action" value="insert">
    <label>Patient ID</label><input type="number" name="patientId" required>
    <label>Amount</label><input type="number" step="0.01" name="amount" required>
    <label>Date</label><input type="date" name="billingDate" required>
    <label>Description</label><textarea name="description" rows="2"></textarea>
    <button type="submit">Save</button>
  </form>
</div>
</body>
</html>
