<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<!DOCTYPE html>
<html lang="${sessionScope.lang}">
<head>
    <title>update_user</title>
</head>
<body>

<jsp:include page="/header.jsp"></jsp:include>
<br>
<div style = "position:relative; left:5px; top:2px;">
    <h2><fmt:message key="label.user.update" /></h2><br />

    <form method="get" action="<c:url value='/front_controller'/>">
        <input type="text" hidden name="command" value="utils.DispatchToAdminMain" />
        <input type="submit" name="back" value="<fmt:message key="button.back" />"/>
    </form>

    <div><fmt:message key="id" /> <c:out value="${requestScope.user.userId}"/> </div>
    <div><fmt:message key="name" /> <c:out value="${requestScope.user.name}"/> </div>
    <div><fmt:message key="label.role" /> <c:out value="${requestScope.user.role}"/> </div>

    <br />
    <%--action="<c:url value='/user_update'/>--%>
    <form method="post" action="<c:url value='/front_controller'/>">
        <input type="text" hidden name="command" value="user.UpdateUser" />
        <label> <fmt:message key="label.new.username" /> <input type="text" name="name" required/></label><br>

        <label> <fmt:message key="label.new.role" /> <select name="role" >
            <option><fmt:message key="label.role.admin" /></option>
            <option><fmt:message key="label.role.user" /></option>
            </select>
        </label><br>

        <input type="number" hidden name="id" value="${requestScope.user.userId}"/>

        <input type="submit" value="<fmt:message key="button.ok" />" name="Ok"><br>
    </form>
</div>

</body>
</html>
