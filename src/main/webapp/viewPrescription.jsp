<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>View Prescription</title>
  <style>
    table { border-collapse: collapse; width: 60%; margin: 20px 0; }
    th, td { border: 1px solid #ccc; padding: 8px; text-align: left; }
    th { background: #f4f4f4; }
    .btn { display: inline-block; padding: 8px 12px; background: #007bff; color: white; text-decoration: none; border-radius: 4px; }
  </style>
</head>
<body>

  <h1>Prescription &amp; Diagnosis Details</h1>

  <c:choose>
    <c:when test="${not empty prescription}">
      <table>
        <tr>
          <th>Appointment ID</th>
          <td>${prescription.appointmentId}</td>
        </tr>
        <tr>
          <th>Patient Name</th>
          <td>${prescription.patientName}</td>
        </tr>
        <tr>
          <th>Doctor Name</th>
          <td>${prescription.doctorName}</td>
        </tr>
        <tr>
          <th>Date</th>
          <td><fmt:formatDate value="${prescription.date}" pattern="yyyy‑MM‑dd" /></td>
        </tr>
        <tr>
          <th>Diagnosis</th>
          <td><pre style="white-space: pre-wrap;">${prescription.diagnosis}</pre></td>
        </tr>
        <tr>
          <th>Prescription</th>
          <td><pre style="white-space: pre-wrap;">${prescription.prescriptionText}</pre></td>
        </tr>
      </table>
    </c:when>
    <c:otherwise>
      <p><em>No prescription found.</em></p>
    </c:otherwise>
  </c:choose>

  <p>
    <a href="doctorHome.jsp" class="btn">Back to Dashboard</a>
    <a href="prescriptionList" class="btn" style="background:#28a745;">Back to List</a>
  </p>

</body>
</html>
