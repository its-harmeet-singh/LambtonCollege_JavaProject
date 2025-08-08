<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"
         isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"     prefix="c"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8"/>
  <title>Edit Diagnosis</title>
  <link rel="stylesheet" href="css/style.css"/>
</head>
<body>
  <div class="container">
    <h1>Edit Diagnosis</h1>
    <form action="diagnosis" method="post">
      <input type="hidden" name="action"        value="update"/>
      <input type="hidden" name="id"            value="${diagnosis.id}"/>
      <input type="hidden" name="appointmentId" value="${appointment.id}"/>
      <input type="hidden" name="patientId" value="${appointment.patientId}" />
      <input type="hidden" name="doctorId"      value="${doctorId}" />


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
               value="Dr. ${doctorName}"
               readonly/>
      </div>

      <div class="form-group">
        <label for="diagnosis">Diagnosis</label>
        <textarea id="diagnosis"
                  name="diagnosis"
                  class="form-control"
                  rows="3"
                  required>${diagnosis.diagnosis}</textarea>
      </div>

      <div class="form-group">
        <label for="dateDiagnosed">Date Diagnosed</label>
        <input type="date"
               id="dateDiagnosed"
               name="dateDiagnosed"
               class="form-control"
               value="${diagnosis.dateDiagnosed}"
               required/>
      </div>

      <div class="form-group">
        <label for="notes">Notes</label>
        <textarea id="notes"
                  name="notes"
                  class="form-control"
                  rows="3">${diagnosis.notes}</textarea>
      </div>

      <button type="submit" class="button">Update Dx</button>
    </form>
  </div>
</body>
</html>
