<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"
         isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8"/>
  <title>Prescription &amp; Diagnosis</title>
  <link rel="stylesheet" href="css/style.css"/>
</head>
<body>
  <div class="container">
    <h1>Prescription &amp; Diagnosis</h1>
    <a href="patientHome" class="button">Back to Home</a>

    <h2>Prescriptions</h2>
    <table>
      <tr>
        <th>Medicine</th><th>Dosage</th><th>Date</th><th>Instructions</th>
      </tr>
      <c:forEach var="p" items="${scripts}">
        <tr>
          <td>${p.medicine}</td>
          <td>${p.dosage}</td>
          <td>${p.dateIssued}</td>
          <td>${p.instructions}</td>
        </tr>
      </c:forEach>
    </table>

    <h2>Diagnoses</h2>
    <table>
      <tr>
        <th>Diagnosis</th><th>Date</th><th>Notes</th>
      </tr>
      <c:forEach var="d" items="${diags}">
        <tr>
          <td>${d.diagnosis}</td>
          <td>${d.dateDiagnosed}</td>
          <td>${d.notes}</td>
        </tr>
      </c:forEach>
    </table>
  </div>
</body>
</html>
