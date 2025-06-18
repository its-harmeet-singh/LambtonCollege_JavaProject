<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"
         session="true"
         isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8"/>
  <title>New Appointment</title>
  <link rel="stylesheet" href="css/style.css"/>
</head>
<body>
  <div class="container">
    <h1>Schedule New Appointment</h1>
    <form action="appointments" method="post">
      <input type="hidden" name="action" value="insert"/>

      <!-- Patient: if session role is PATIENT, show name+hidden ID; otherwise show dropdown -->
      <c:choose>
        <c:when test="${sessionScope.role == 'PATIENT'}">
          <label for="patientId">Patient</label>
          <input type="text"
                 id="patient"
                 value="${sessionScope.user.name}"
                 readonly/>
          <input type="hidden"
                 id="patientId"
                 name="patientId"
                 value="${sessionScope.user.id}"/>
        </c:when>
        <c:otherwise>
          <label for="patientId">Patient</label>
          <select id="patientId" name="patientId" required>
            <c:forEach var="p" items="${patients}">
              <option value="${p.id}">${p.name}</option>
            </c:forEach>
          </select>
        </c:otherwise>
      </c:choose>

      <!-- Doctor dropdown always editable -->
      <label for="doctorId">Doctor</label>
      <select id="doctorId" name="doctorId" required>
        <c:forEach var="d" items="${doctors}">
          <option value="${d.id}">${d.name}</option>
        </c:forEach>
      </select>

      <label for="appointmentTime">Time</label>
      <input type="datetime-local"
             id="appointmentTime"
             name="appointmentTime"
             required/>

      <label for="reason">Reason</label>
      <textarea id="reason"
                name="reason"
                rows="3"></textarea>

      <button type="submit">Save</button>
    </form>
  </div>
</body>
</html>
