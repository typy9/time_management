<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<!DOCTYPE html>
<html lang="${sessionScope.lang}">
<head>
    <title>admin</title>
    <link href="css/adminMenuStyle.css" rel="stylesheet" type="text/css">
</head>
<body>

<jsp:include page="/header.jsp"></jsp:include>

<br>
<div style = "position:relative; left:10px; top:2px;">

    <h1><fmt:message key="label.admin.cabinet" /></h1>
    <%--action="<c:url value='/users_list'/>--%>
    <form id="users_form" method="get" action="<c:url value='/front_controller'/>">
        <input type="submit" value="<fmt:message key="button.users" />"/>
        <input type="text" hidden name="command" value="user.DisplayUser" />
    </form>
    <%--action="<c:url value='/activities_list'/>"--%>
    <form id="activities_form" method="get" action="<c:url value='/front_controller'/>">
        <input type="submit" name="activities" value="<fmt:message key="button.activities" />"/>
        <input type="text" hidden name="command" value="activity.DisplayActivity" />
    </form>
    <%--action="<c:url value='/categories_list'/>"--%>
    <form id="categories_form" method="get" action="<c:url value='/front_controller'/>">
        <input type="submit" name="categories" value="<fmt:message key="button.categories" />"/>
        <input type="text" hidden name="command" value="category.DisplayCategory" />
    </form>
    <%--action="<c:url value='/users_report'/>"--%>
    <form id="reports_form" method="get" action="<c:url value='/front_controller'/>">
        <input type="submit" name="reports" value="<fmt:message key="label.button.reports" />"/>
        <input type="text" hidden name="command" value="report.DisplayReport" />
    </form>
    <%--action="<c:url value='/requests'/>"--%>
    <form id="requests_form" method="get" action="<c:url value='/front_controller'/>">
        <input type="submit" name="requests" value="<fmt:message key="label.button.requests" />"/>
        <input type="text" hidden name="command" value="request.DisplayRequest" />
    </form>

    <a href="<c:url value='/logout' />"><fmt:message key="ref.logout" /></a>
</div>

</body>
</html>
