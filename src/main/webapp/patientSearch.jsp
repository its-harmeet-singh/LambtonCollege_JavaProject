<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8"/>
  <title>Patient Search</title>
  <link rel="stylesheet" href="css/style.css"/>
</head>
<body>
  <div class="container">
    <h1>Patient Search</h1>

    <!-- Top actions -->
    <div style="margin-bottom: 12px;">
      <c:choose>
        <c:when test="${isAdmin}">
          <a href="adminHome.jsp" class="button">Go to Admin Dashboard</a>
        </c:when>
        <c:otherwise>
          <a href="doctorHome.jsp" class="button">Go to Doctor Dashboard</a>
        </c:otherwise>
      </c:choose>
    </div>

    <!-- Search form -->
    <form method="post" action="patientSearch" class="search-form">

      <div class="form-row">
        <label for="name">Name</label>
        <input id="name" type="text" name="name"
               value="${fn:escapeXml(name)}"
               placeholder="e.g. John"/>
      </div>

      <div class="form-row">
        <label for="date">Date</label>
        <input id="date" type="date" name="date" value="${date}"/>
      </div>

      <button type="submit" class="button">Search</button>
    </form>
    <br>
    <hr/>

    <!-- Results -->
    <c:if test="${not empty name or not empty date or not empty results}">
      <h2>Results</h2>

      <c:choose>
        <c:when test="${empty results}">
          <p>No patients found.</p>
        </c:when>
        <c:otherwise>
          <table>
            <tr>
              <th>Patient</th>
              <th>Email Id</th>
              <th>Appointment Date</th>
              <th>Doctor</th>
              <th>Actions</th>
            </tr>

            <c:forEach var="r" items="${results}">
              <tr>
                <td><c:out value="${r.patientName}"/></td>
                <td><c:out value="${r.phone}"/></td>
                <td><c:out value="${r.appointmentDate}"/></td>
                <td><c:out value="${r.doctorName}"/></td>
                <td class="action-buttons">
                  <c:choose>
                    <c:when test="${not empty r.appointmentId}">
                      <a href="prescription?action=edit&amp;appointmentId=${r.appointmentId}" class="button">Update Rx</a>
                      <a href="diagnosis?action=edit&amp;appointmentId=${r.appointmentId}"  class="button">Update Dx</a>
                    </c:when>
                    <c:otherwise>
                      <span class="button disabled" aria-disabled="true" title="No appointment">Update Rx</span>
                      <span class="button disabled" aria-disabled="true" title="No appointment">Update Dx</span>
                    </c:otherwise>
                  </c:choose>

                  <c:if test="${isAdmin}">
                    <a href="billing?action=edit&amp;id=${r.patientId}" class="button">Update Billing</a>
                  </c:if>
                </td>

              </tr>
            </c:forEach>
          </table>
        </c:otherwise>
      </c:choose>
    </c:if>
  </div>

</body>
</html>
