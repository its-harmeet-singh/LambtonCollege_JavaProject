<html>
<head>
  <link rel="stylesheet" href="css/style.css">
  <title>Add Doctor</title>
</head>
<body>
<div class="container">
  <h1>Add New Doctor</h1>
  <form action="doctors" method="post">
    <input type="hidden" name="action" value="insert">
    <label>Name</label><input type="text" name="name" required>
    <label>Specialty</label><input type="text" name="specialty">
    <label>Phone</label><input type="text" name="phone">
    <label>Email</label><input type="text" name="email">
    <button type="submit">Save</button>
  </form>
</div>
</body>
</html>
