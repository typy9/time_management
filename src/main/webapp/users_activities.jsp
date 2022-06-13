<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctags" uri="/WEB-INF" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<!DOCTYPE html>
<html lang="${sessionScope.lang}">
<head>
    <title>users-activities</title>
</head>
<body>

<jsp:include page="/header.jsp"></jsp:include>

<br>
<div style = "position:relative; left:5px; top:2px;">
    <h1><fmt:message key="label.list.users.activities" /></h1><br />

    <form method="get" action="<c:url value='/front_controller'/>">
        <input type="text" hidden name="command" value="utils.DispatchToAdminMain" />
        <input type="submit" name="back" value="<fmt:message key="button.back" />"/>
    </form>

    <c:forEach var="userActivity" items="${requestScope.usersActivitiesFull}">
        <ul>

            <li><fmt:message key="id" /> <c:out value="${userActivity.id}"/></li>
            <li><fmt:message key="label.user.name" /> <c:out value="${userActivity.user_name}"/></li>
            <li><fmt:message key="label.activity" /> <c:out value="${userActivity.activity_name}"/></li>
            <li><fmt:message key="label.time" /> <c:out value="${userActivity.time}"/></li>

        </ul>
        <hr />

    </c:forEach>

    <%--pagination--%>
    <%--For displaying Previous link except for the 1st page --%>
    <c:if test="${requestScope.currentPage != 1}">
        <td><a href="users_report?page=${requestScope.currentPage - 1}"><fmt:message key="pagination.previous" /></a></td>
    </c:if>

    <%--For displaying Page numbers.
        The when condition does not display a link for the current page--%>
    <ctags:display_pagination
            currentPage="${requestScope.currentPage}"
            noOfPages="${requestScope.noOfPages}"
            page="users_report"/>
<%--    <c:forEach begin="1" end="${requestScope.noOfPages}" var="i">--%>
<%--        <c:choose>--%>
<%--            <c:when test="${requestScope.currentPage eq i}">--%>
<%--                <td>${i}</td>--%>
<%--            </c:when>--%>
<%--            <c:otherwise>--%>
<%--                <td><a href="users_report?page=${i}">${i}</a></td>--%>
<%--            </c:otherwise>--%>
<%--        </c:choose>--%>
<%--    </c:forEach>--%>

    <%--For displaying Next link --%>
    <c:if test="${requestScope.currentPage lt requestScope.noOfPages}">
        <td><a href="users_report?page=${requestScope.currentPage + 1}"><fmt:message key="pagination.next" /></a></td>
    </c:if>
</div>

</body>
</html>