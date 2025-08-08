<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"
         isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"     prefix="c"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Book Appointment</title>
  <link rel="stylesheet" href="css/style.css"/>
</head>
<body>
  <div class="container">
    <h1>Book an Appointment</h1>

    <c:if test="${not empty error}">
      <div class="error">${error}</div>
    </c:if>
    <c:if test="${param.success == '1'}">
      <div class="success">Appointment booked successfully!</div>
    </c:if>

    <form method="post" action="patientBookAppointment">
      <!-- Doctor -->
      <div>
        <label for="doctorId">Doctor</label>
        <select id="doctorId" name="doctorId" required>
          <option value="">-- Select Doctor --</option>
          <c:forEach var="d" items="${doctors}">
            <option value="${d.id}">${d.name} (${d.specialty})</option>
          </c:forEach>
        </select>
      </div>

      <!-- Date -->
      <div>
        <label for="appointmentDate">Date</label>
        <input type="date" id="appointmentDate" name="appointmentDate" required />
      </div>

      <!-- Time -->
      <div>
        <label for="appointmentTime">Time</label>
        <input type="time" id="appointmentTime" name="appointmentTime" required />
      </div>

      <!-- Reason -->
      <div>
        <label for="reason">Reason / Notes</label>
        <textarea id="reason" name="reason" rows="3" maxlength="500"
                  placeholder="Describe your concern..."></textarea>
      </div>

      <div>
        <button type="submit" class="button">Confirm Booking</button>
        <a href="patientHome" class="button">Cancel</a>
      </div>
    </form>
  </div>
</body>
</html>
