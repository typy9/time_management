<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<!DOCTYPE html>
<html lang="${sessionScope.lang}">
<head>
  <title>update_activity</title>
</head>

<body>

<jsp:include page="/header.jsp"></jsp:include>

<br>
<div style = "position:relative; left:5px; top:2px;">
  <h2><fmt:message key="label.update.activity" /></h2><br />
  <form method="get" action="<c:url value='/front_controller'/>">
    <input type="text" hidden name="command" value="utils.DispatchToAdminMain" />
    <input type="submit" name="back" value="<fmt:message key="button.back" />"/>
  </form>

  <div><fmt:message key="id" /> <c:out value="${requestScope.activity.activityId}"/> </div>
  <div><fmt:message key="name" /> <c:out value="${requestScope.activity.name}"/> </div>
  <div><fmt:message key="category" /> <c:out value="${requestScope.activity.category}"/> </div>
  <br />

  <%--pass /activity_update throug front controller--%>
  <form method="post" action="<c:url value='/front_controller'/>">
    <input type="hidden" name="command" value="activity.UpdateActivity" /> <input>
    <label> <fmt:message key="label.new.name" /> <input type="text" name="name" required/></label><br>

    <label> <fmt:message key="label.new.category" /> <input type="text" name="category" required/></label><br>

    <input type="number" hidden name="id" value="${requestScope.activity.activityId}"/>

    <input type="submit" value="<fmt:message key="button.ok" />" name="Ok"><br>
  </form>
</div>

</body>
</html>
