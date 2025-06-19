<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"
         isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"     prefix="c"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8"/>
  <title>New Prescription</title>
  <link rel="stylesheet" href="css/style.css"/>
</head>
<body>
  <div class="container">
    <h1>New Prescription</h1>
    <form action="prescription" method="post">
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
        <label for="medicine">Medicine</label>
        <input type="text"
               id="medicine"
               name="medicine"
               class="form-control"
               placeholder="e.g. Amoxicillin"
               required/>
      </div>

      <div class="form-group">
        <label for="dosage">Dosage</label>
        <input type="text"
               id="dosage"
               name="dosage"
               class="form-control"
               placeholder="e.g. 500mg TID"
               required/>
      </div>

      <div class="form-group">
        <label for="dateIssued">Date Issued</label>
        <input type="date"
               id="dateIssued"
               name="dateIssued"
               class="form-control"
               value="${fn:substringBefore(appointment.appointmentTime,'T')}"
               required/>
      </div>

      <div class="form-group">
        <label for="instructions">Instructions</label>
        <textarea id="instructions"
                  name="instructions"
                  class="form-control"
                  rows="3"
                  placeholder="Take after meals, etc."></textarea>
      </div>

      <button type="submit" class="button">Save Rx</button>
    </form>
  </div>
</body>
</html>
