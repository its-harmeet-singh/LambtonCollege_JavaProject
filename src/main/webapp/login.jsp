<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8"/>
  <title>Login</title>
  <link rel="stylesheet" href="css/style.css"/>
</head>
<body>
  <div class="container">
    <h1>Sign In</h1>
    <form action="login" method="post">
      <label for="email">Email</label>
      <input type="email" id="email" name="email" required/>

      <label for="password">Password</label>
      <input type="password" id="password" name="password" required/>

      <button type="submit">Login</button>
    </form>
    <c:if test="${not empty error}">
      <p style="color:red">${error}</p>
    </c:if>
  </div>
</body>
</html>
