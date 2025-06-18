<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"
         isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8"/>
  <title>Sign In</title>
  <link rel="stylesheet" href="css/style.css"/>
  <style>
    /* optional inline overrides just for login layout */
    .login-container {
      max-width: 400px;
      margin: 3rem auto;
    }
    .login-container form label {
      display: block;
      margin-top: 1rem;
    }
    .login-container form input {
      width: 100%;
      padding: .5rem;
      margin-top: .25rem;
      box-sizing: border-box;
    }
    .login-container .error {
      color: #c0392b;
      margin-bottom: 1rem;
    }
  </style>
</head>
<body>
  <div class="container login-container">
    <h1>Sign In</h1>

    <c:if test="${not empty error}">
      <p class="error">${error}</p>
    </c:if>

    <form action="login" method="post">
      <label for="email">Email</label>
      <input type="email"
             id="email"
             name="email"
             placeholder="you@example.com"
             required/>

      <label for="password">Password</label>
      <input type="password"
             id="password"
             name="password"
             placeholder="••••••••"
             required/>

      <button type="submit" class="button" style="margin-top:1.5rem;">Login</button>
    </form>
  </div>
</body>
</html>
