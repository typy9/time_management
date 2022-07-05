<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctags" uri="/WEB-INF" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<!DOCTYPE html>
<html lang="${sessionScope.lang}">
<head>
    <title>categories</title>
</head>
<body>
<jsp:include page="/header.jsp"></jsp:include>

<br>
<div style = "position:relative; left:5px; top:2px;">
    <h1><fmt:message key="label.list.categories" /></h1><br />

    <form method="get" action="<c:url value='/front_controller'/>">
        <input type="text" hidden name="command" value="utils.DispatchToAdminMain" />
        <input type="submit" name="back" value="<fmt:message key="button.back" />"/>
    </form>


    <c:forEach var="category" items="${requestScope.categories}">
        <ul>

            <li><fmt:message key="id" /> <c:out value="${category.categoryId}"/></li>
            <li><fmt:message key="label.category.name" /> <c:out value="${category.name}"/></li>
            <%--action="<c:url value='/category_delete'/>--%>
            <form method="post" action="<c:url value='/front_controller'/>">
                <input type="text" hidden name="command" value="category.DeleteCategory" />
                <input type="number" hidden name="id" value="${category.categoryId}" />
                <input type="submit" name="delete" value="<fmt:message key="button.delete" />"/>
            </form>
            <%--action="<c:url value='/category_update'/>--%>
            <form method="get" action="<c:url value='/front_controller'/>">
                <input type="text" hidden name="command" value="category.UpdateCategory" />
                <input type="number" hidden name="id" value="${category.categoryId}" />
                <input type="submit" name="update" value="<fmt:message key="button.update" />"/>
            </form>
        </ul>
        <hr />

    </c:forEach>

    <%--pagination--%>
    <%--For displaying Previous link except for the 1st page --%>
    <c:if test="${requestScope.currentPage != 1}">
        <td><a href="categories_list?page=${requestScope.currentPage - 1}"><fmt:message key="pagination.previous" /></a></td>
    </c:if>

    <%--For displaying Page numbers.
        The when condition does not display a link for the current page--%>
    <ctags:display_pagination
            currentPage="${requestScope.currentPage}"
            noOfPages="${requestScope.noOfPages}"
            page="categories_list"/>
<%--    <c:forEach begin="1" end="${requestScope.noOfPages}" var="i">--%>
<%--        <c:choose>--%>
<%--            <c:when test="${requestScope.currentPage eq i}">--%>
<%--                <td>${i}</td>--%>
<%--            </c:when>--%>
<%--            <c:otherwise>--%>
<%--                <td><a href="categories_list?page=${i}">${i}</a></td>--%>
<%--            </c:otherwise>--%>
<%--        </c:choose>--%>
<%--    </c:forEach>--%>

    <%--For displaying Next link --%>
    <c:if test="${requestScope.currentPage lt requestScope.noOfPages}">
        <td><a href="categories_list?page=${requestScope.currentPage + 1}"><fmt:message key="pagination.next" /></a></td>
    </c:if>

    <h2><fmt:message key="label.add.category" /></h2><br />
    <%--action="<c:url value='/category_add'/>--%>
    <form method="post" action="<c:url value='/front_controller'/>">
        <input type="text" hidden name="command" value="category.AddCategory" />

        <fmt:message key="name" /> <label><input type="text" name="name" required
                                                 pattern="[a-zA-Z]+" minlength="4" maxlength="10"
                                                 title="Letters only. Min length 4, max 10."></label><br>

        <input type="submit" value="<fmt:message key="button.ok" />" name="Ok"><br>
    </form>
</div>
</body>
</html>
