<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<!DOCTYPE html>
<html lang="${sessionScope.lang}">
<head>
    <title>update-activity-time</title>
</head>
<body>

<jsp:include page="/header.jsp"></jsp:include>
<div style = "position:relative; left:5px; top:2px;">
    <h2><fmt:message key="label.time.update" /></h2><br />

    <div><fmt:message key="id" /> <c:out value="${sessionScope.user.userId}"/> </div>
    <div><fmt:message key="name" /> <c:out value="${sessionScope.user.name}"/> </div>
    <div><fmt:message key="label.role" /> <c:out value="${sessionScope.user.role}"/> </div>

    <br />
    <%--action="<c:url value='/update_time'/>"--%>
    <form method="post" action="<c:url value='/front_controller'/>">
        <input type="text" hidden name="command" value="activity.UpdateActivityTime" />

        <div><fmt:message key="label.activity" /> <c:out value="${sessionScope.userActivity.activity_name}"/> </div><br>

        <label> <fmt:message key="label.time" /> <input type="text" name="time" /></label><br>

        <input type="number" hidden name="activity_id" value="${sessionScope.userActivity.id}"/>

        <input type="number" hidden name="user_id" value="${sessionScope.user.userId}" />

        <input type="submit" value="<fmt:message key="button.ok" />" name="Ok"><br>

    </form>
</div>

</body>
</html>
