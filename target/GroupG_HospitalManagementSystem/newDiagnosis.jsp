<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"
         isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"     prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8"/>
  <title>New Diagnosis</title>
  <link rel="stylesheet" href="css/style.css"/>
</head>
<body>
  <div class="container">
    <h1>New Diagnosis</h1>
    <form action="diagnosis" method="post">
      <input type="hidden" name="action"        value="insert"/>
      <input type="hidden" name="appointmentId" value="${appointment.id}"/>

      <div class="form-group">
        <label>Patient</label>
        <input type="text"
               class="form-control"
               value="${patientMap[appointment.patientId]}"
               readonly/>
      </div>

      <div class="form-group">
        <label>Doctor</label>
        <input type="text"
               class="form-control"
               value="Dr. ${user.name}"
               readonly/>
      </div>

      <div class="form-group">
        <label for="diagnosis">Diagnosis</label>
        <textarea id="diagnosis"
                  name="diagnosis"
                  class="form-control"
                  rows="3"
                  required></textarea>
      </div>

      <div class="form-group">
        <label for="dateDiagnosed">Date Diagnosed</label>
        <input type="date"
               id="dateDiagnosed"
               name="dateDiagnosed"
               class="form-control"
               value="${fn:substringBefore(appointment.appointmentTime,'T')}"
               required/>
      </div>

      <div class="form-group">
        <label for="notes">Notes</label>
        <textarea id="notes"
                  name="notes"
                  class="form-control"
                  rows="3"></textarea>
      </div>

      <button type="submit" class="button">Save Dx</button>
    </form>
  </div>
</body>
</html>
