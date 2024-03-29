<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctags" uri="/WEB-INF" %>


<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<!DOCTYPE html>
<html lang="${sessionScope.lang}">

<head>
  <title>user_menu</title>
</head>

<body>
<jsp:include page="/header.jsp"></jsp:include>
<br>

<div style = "position:relative; left:5px; top:2px;">
  <h1><fmt:message key="label.user.cabinet" /></h1>
  <h3><fmt:message key="label.hello" /> ${sessionScope.user.name}</h3>
  <h3><fmt:message key="label.user.id" /> ${sessionScope.user.userId}</h3>
  <%--<li> User Login: <c:out value="${sessionScope.login}"/></li>--%>
  <%--<li> User Password: <c:out value="${sessionScope.password}"/></li>--%>

  <h2><fmt:message key="label.list.activities" /></h2><br />
  <c:forEach var="userActivity" items="${sessionScope.userActivities}">
    <ul>
      <li><fmt:message key="id" /> <c:out value="${userActivity.id}"/></li>
      <li><fmt:message key="label.activity" /> <c:out value="${userActivity.activity_name}"/></li>
      <li><fmt:message key="label.time" /> <c:out value="${userActivity.time}"/></li>
    </ul>
    <%-- action="<c:url value='/update_time'/>" --%>
    <form method="get" action="<c:url value='/front_controller'/>">
      <input type="text" hidden name="command" value="activity.UpdateActivityTime" />
      <input type="number" hidden name="activity_id" value="${userActivity.id}" />
      <input type="number" hidden name="user_id" value="${sessionScope.user.userId}" />
      <input type="submit" value="<fmt:message key="button.time" />"/>
    </form>
    <form method="get" action="<c:url value='/front_controller'/>">
      <input type="text" hidden name="command" value="request.DeleteRequestUser" />
      <input type="number" hidden name="activity_id" value="${userActivity.id}" />
      <input type="number" hidden name="user_id" value="${sessionScope.user.userId}" />
      <input type="submit" value="<fmt:message key="button.delete" />"/>
    </form>
    <hr />

  </c:forEach>


  <%--pagination--%>
  <%--For displaying Previous link except for the 1st page --%>
  <c:if test="${requestScope.currentPage != 1}">
    <td><a href="user_cabinet?page=${requestScope.currentPage - 1}"><fmt:message key="pagination.previous" /></a></td>
  </c:if>

  <%--For displaying Page numbers.
      The when condition does not display a link for the current page--%>
  <ctags:display_pagination
          currentPage="${requestScope.currentPage}"
          noOfPages="${requestScope.noOfPages}"
          page="user_cabinet"/>

  <%--For displaying Next link --%>
  <c:if test="${requestScope.currentPage lt requestScope.noOfPages}">
    <td><a href="user_cabinet?page=${requestScope.currentPage + 1}"><fmt:message key="pagination.next" /></a></td>
  </c:if>


  <h2><fmt:message key="label.add.new.activity" /></h2><br />
  <%--action="<c:url value='/add_activity_request'/>"--%>
  <form method="post" action="<c:url value='/front_controller'/>">
    <input type="text" hidden name="command" value="request.AddRequest" />
    <input type="number" hidden name="user_id" value="${sessionScope.user.userId}" />

    <fmt:message key="label.activity" /> <label>
    <select name="activity">
      <option><fmt:message key="label.admin" /></option>
      <option><fmt:message key="label.management" /></option>
      <option><fmt:message key="label.pause" /></option>
    </select>
  </label><br>
    <input type="submit" name="Send Request" value="<fmt:message key="button.send" />" /><br>
  </form>
  <br>
  <a href="<c:url value="/logout"/>"><fmt:message key="ref.logout" /></a>
</div>

</body>
</html>