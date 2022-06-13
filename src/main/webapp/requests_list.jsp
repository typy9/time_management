<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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

        </ul>
        <hr />

    </c:forEach>
</div>

</body>
</html>