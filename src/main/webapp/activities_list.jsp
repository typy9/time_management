<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctags" uri="/WEB-INF" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<!DOCTYPE html>
<html lang="${sessionScope.lang}">
<head>
    <title>activities</title>
</head>
<body>
<jsp:include page="/header.jsp"></jsp:include>

<br>
<div style = "position:relative; left:5px; top:2px;">
    <h1><fmt:message key="label.activities" /></h1><br />

    <form method="get" action="<c:url value='/front_controller'/>">
        <input type="text" hidden name="command" value="utils.DispatchToAdminMain" />
        <input type="submit" name="back" value="<fmt:message key="button.back" />"/>
    </form>
    <br>
    <%--<div style="padding: 5px;">--%>
    <a href="<c:url value='/activity_sort_by_name' />"><fmt:message key="ref.sort.name" /></a>
    <a href="<c:url value='/activity_sort_by_category' />"><fmt:message key="ref.sort.category" /></a>
    <a href="<c:url value='/activity_sort_by_number_of_users' />"><fmt:message key="ref.sort.users" /></a>

    <h3><fmt:message key="filter.category" /></h3><br />
    <%--action="<c:url value='/filter_by_category'/>"--%>
    <form method="post" action="<c:url value='/front_controller'/>">
        <input type="text" hidden name="command" value="activity.FilterActivityByCategory" />
        <fmt:message key="category.id" /> <label><input type="text" name="id"></label><br>
        <input type="submit" value="<fmt:message key="button.ok" />" name="Ok"><br>
    </form>
    <hr />

        <h2><fmt:message key="label.list.activities" /></h2><br />

    <c:forEach var="activity" items="${requestScope.activities}">
        <ul>

            <li><fmt:message key="id" /> <c:out value="${activity.activityId}"/></li>
            <li><fmt:message key="name" /> <c:out value="${activity.name}"/></li>
            <li><fmt:message key="category" /> <c:out value="${activity.category}"/></li>
            <%--action="<c:url value='/activity_delete'/>"--%>
            <form method="post" action="<c:url value='/front_controller'/>">
                <input type="text" hidden name="command" value="activity.DeleteActivity" />
                <input type="number" hidden name="id" value="${activity.activityId}" />
                <input type="submit" name="delete" value="<fmt:message key="button.delete" />"/>
            </form>
            <%--action="<c:url value='/activity_update'/>"--%>
            <form method="get" action="<c:url value='/front_controller'/>">
                <input type="text" hidden name="command" value="activity.UpdateActivity" />
                <input type="number" hidden name="id" value="${activity.activityId}" />
                <input type="submit" value="<fmt:message key="button.update" />"/>
            </form>
        </ul>
        <hr />

    </c:forEach>

    <%--pagination--%>
    <%--For displaying Previous link except for the 1st page --%>
    <c:if test="${requestScope.currentPage != 1}">
        <td><a href="activities_list?page=${requestScope.currentPage - 1}"><fmt:message key="pagination.previous" /></a></td>
    </c:if>

    <%--For displaying Page numbers.
        The when condition does not display a link for the current page--%>
    <ctags:display_pagination
            currentPage="${requestScope.currentPage}"
            noOfPages="${requestScope.noOfPages}"
            page="activities_list"/>
<%--    <c:forEach begin="1" end="${requestScope.noOfPages}" var="i">--%>
<%--        <c:choose>--%>
<%--            <c:when test="${requestScope.currentPage eq i}">--%>
<%--                <td>${i}</td>--%>
<%--            </c:when>--%>
<%--            <c:otherwise>--%>
<%--                <td><a href="activities_list?page=${i}">${i}</a></td>--%>
<%--            </c:otherwise>--%>
<%--        </c:choose>--%>
<%--    </c:forEach>--%>

    <%--For displaying Next link --%>
    <c:if test="${requestScope.currentPage lt requestScope.noOfPages}">
        <td><a href="activities_list?page=${requestScope.currentPage + 1}"><fmt:message key="pagination.next" /></a></td>
    </c:if>

    <h3><fmt:message key="label.new.activity" /></h3><br />


    <form method="post" action="<c:url value='/front_controller'/>">
        <input type="hidden" name="command" value="activity.AddActivity" />
        <fmt:message key="name" /> <label><input type="text" name="name" required
                                                 pattern="[a-zA-Z]+" minlength="4" maxlength="10"
                                                 title="Letters only. Min length 4, max 10."></label><br>

        <fmt:message key="category" /> <label><input type="text" name="category" required
                                                     pattern="[1-4]{1}"
                                                     title="Digits only."></label><br>

        <input type="submit" value=<fmt:message key="button.ok" /> name="Ok"><br>
    </form>
</div>
</body>
</html>
