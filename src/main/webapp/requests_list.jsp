<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctags" uri="/WEB-INF" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<!DOCTYPE html>
<html lang="${sessionScope.lang}">

<head>
    <title>requests</title>
</head>
<body>

<jsp:include page="/header.jsp"></jsp:include>
<br>
<div style = "position:relative; left:5px; top:2px;">
    <h1><fmt:message key="label.requests" /></h1><br />

    <form method="get" action="<c:url value='/front_controller'/>">
        <input type="text" hidden name="command" value="utils.DispatchToAdminMain" />
        <input type="submit" name="back" value="<fmt:message key="button.back" />"/>
    </form>

    <h2><fmt:message key="label.list.requests" /></h2><br />

    <c:forEach var="activityRequest" items="${requestScope.activityRequests}">
        <ul>
            <li><fmt:message key="id" /> <c:out value="${activityRequest.request_id}"/></li>
            <li><fmt:message key="label.user.id" /> <c:out value="${activityRequest.userId}"/></li>
            <li><fmt:message key="label.activity.id" /> <c:out value="${activityRequest.activityId}"/></li>
            <li><fmt:message key="label.time" /> <c:out value="${activityRequest.time}"/></li>
            <li><fmt:message key="label.status" /> <c:out value="${activityRequest.status}"/></li>
            <%--action="<c:url value='/approve_request'/>"--%>
            <form id="approveForm" method="post" action="<c:url value='/front_controller'/>" >
                <label>
                    <input type="text" hidden name="command" value="request.ApproveRequest" />
                </label>
                <label>
                    <input type="number" hidden name="id" value="${activityRequest.request_id}" />
                </label>
                <label>
                    <input type="number" hidden name="user_id" value="${activityRequest.userId}" />
                </label>
                <label>
                    <input type="number" hidden name="activity_id" value="${activityRequest.activityId}" />
                </label>
                <input type="submit" id='btnApp' name="approve" value="<fmt:message key="button.approve" />"/>
            </form>
            <%--action="<c:url value='/decline_request'/>"--%>
            <form method="post" action="<c:url value='/front_controller'/>">
                <label>
                    <input type="text" hidden name="command" value="request.DeclineRequest" />
                </label>
                <label>
                    <input type="number" hidden name="id" value="${activityRequest.request_id}" />
                </label>
                <input type="submit" id='btnDecl' name="decline" value="<fmt:message key="button.decline" />"/>
            </form>

            <form method="post" action="<c:url value='/front_controller'/>">
                <label>
                    <input type="text" hidden name="command" value="request.DeleteRequestAdmin" />
                </label>
                <label>
                    <input type="number" hidden name="id" value="${activityRequest.request_id}" />
                </label>
                <label>
                    <input type="number" hidden name="user_id" value="${activityRequest.userId}" />
                </label>
                <label>
                    <input type="number" hidden name="activity_id" value="${activityRequest.activityId}" />
                </label>
                <input type="submit" id='btnDell' name="delete" value="<fmt:message key="button.delete" />"/>
            </form>

        </ul>
        <hr />

    </c:forEach>

    <%--pagination--%>
    <%--For displaying Previous link except for the 1st page --%>
    <c:if test="${requestScope.currentPage != 1}">
        <td><a href="requests?page=${requestScope.currentPage - 1}"><fmt:message key="pagination.previous" /></a></td>
    </c:if>
    <%--For displaying Page numbers.
      The when condition does not display a link for the current page--%>
    <ctags:display_pagination
            currentPage="${requestScope.currentPage}"
            noOfPages="${requestScope.noOfPages}"
            page="requests"/>
    <%--For displaying Next link --%>
    <c:if test="${requestScope.currentPage lt requestScope.noOfPages}">
        <td><a href="requests?page=${requestScope.currentPage + 1}"><fmt:message key="pagination.next" /></a></td>
    </c:if>

</div>

</body>
</html>