<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctags" uri="/WEB-INF" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<!DOCTYPE html>
<html lang="${sessionScope.lang}">
<head>
    <title>users</title>
</head>
<body>

<jsp:include page="/header.jsp"></jsp:include>
<br>
<div style = "position:relative; left:5px; top:2px;">
    <h1><fmt:message key="label.users" /></h1><br />

    <form method="get" action="<c:url value='/front_controller'/>">
        <input type="text" hidden name="command" value="utils.DispatchToAdminMain" />
        <input type="submit" name="back" value="<fmt:message key="button.back" />"></input>
    </form>

    <h2><fmt:message key="label.list.users" /> </h2><br />

    <c:forEach var="user" items="${requestScope.users}">
        <ul>

            <li><fmt:message key="id" /> <c:out value="${user.userId}"/></li>
            <li><fmt:message key="name" /> <c:out value="${user.name}"/></li>
            <li><fmt:message key="label.role" /> <c:out value="${user.role}"/></li>
            <%--action="<c:url value='/user_delete'/>--%>
            <form method="post" action="<c:url value='/front_controller'/>">
                <input type="text" hidden name="command" value="user.DeleteUser" />
                <input type="number" hidden name="id" value="${user.userId}" />
                <input type="submit" name="delete" value="<fmt:message key="button.delete" />"/>
            </form>
            <%--action="<c:url value='/user_update'/>--%>
            <form method="get" action="<c:url value='/front_controller'/>">
                <input type="text" hidden name="command" value="user.UpdateUser" />
                <input type="number" hidden name="id" value="${user.userId}" />
                <input type="submit" value="<fmt:message key="button.update" />"/>
            </form>
        </ul>
        <hr />

    </c:forEach>


    <%--pagination--%>
    <%--For displaying Previous link except for the 1st page --%>
    <c:if test="${requestScope.currentPage != 1}">
        <td><a href="users_list?page=${requestScope.currentPage - 1}"><fmt:message key="pagination.previous" /></a></td>
    </c:if>

    <%--For displaying Page numbers.
        The when condition does not display a link for the current page--%>
    <ctags:display_pagination
            currentPage="${requestScope.currentPage}"
            noOfPages="${requestScope.noOfPages}"
            page="users_list"/>

    <%--For displaying Next link --%>
    <c:if test="${requestScope.currentPage lt requestScope.noOfPages}">
        <td><a href="users_list?page=${requestScope.currentPage + 1}"><fmt:message key="pagination.next" /></a></td>
    </c:if>

    <h2><fmt:message key="label.add.user" /></h2><br />
    <%--<form method="post" action="<c:url value='/add_user'/>">--%>
    <form method="post" action="<c:url value='/front_controller'/>">

        <input type="text" hidden name="command" value="user.AddUser" />

        <fmt:message key="label.user.name" /> <label><input type="text" name="name" required
                                                            pattern="[a-zA-Z0-9]+" minlength="1" maxlength="10"
                                                            title="Letters and digits only. Min length 1, max 10."></label><br>
        <fmt:message key="label.login.short" /> <label><input type="text" name="login" required
                                                              pattern="[A-Za-z._]{1,15}" minlength="1" maxlength="15"
                                                              title="Letters, digits and '.' '_' only. Min length 1, max 15."></label><br>
        <fmt:message key="label.password" /> <label><input type="text" name="password" required
                                                           pattern="[A-Za-z._@!#$%^&*()\d]{2,15}" minlength="1" maxlength="15"
                                                           title="Letters, digits and '._@!#$%^&*()' only. Min length 2, max 15."></label><br>
        <fmt:message key="label.role" /><label><select name="role">
        <option>admin</option>
        <option>user</option>
    </select>
    </label><br>
        <input type="submit" value=<fmt:message key="button.ok" /> name="Ok"><br>
    </form>
</div>
</body>
</html>
