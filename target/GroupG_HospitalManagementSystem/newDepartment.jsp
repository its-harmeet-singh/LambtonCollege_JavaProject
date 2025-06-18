<html>
<head>
  <link rel="stylesheet" href="css/style.css">
  <title>Add Department</title>
</head>
<body>
<div class="container">
  <h1>Add New Department</h1>
  <form action="departments" method="post">
    <input type="hidden" name="action" value="insert">
    <label>Name</label><input type="text" name="name" required>
    <label>Location</label><input type="text" name="location">
    <button type="submit">Save</button>
  </form>
</div>
</body>
</html>
