<html>
<head>
  <link rel="stylesheet" href="css/style.css">
  <title>Add Patient</title>
</head>
<body>
<div class="container">
  <h1>Add New Patient</h1>
  <form action="patients" method="post">
    <input type="hidden" name="action" value="insert">
    <label for="name">Name</label>
    <input type="text" id="name" name="name" required>

    <label for="age">Age</label>
    <input type="number" id="age" name="age" required>

    <label for="gender">Gender</label>
    <select id="gender" name="gender">
      <option>Male</option>
      <option>Female</option>
      <option>Other</option>
    </select>

    <label for="address">Address</label>
    <textarea id="address" name="address" rows="3"></textarea>

    <button type="submit">Save</button>
  </form>
</div>
</body>
</html>
