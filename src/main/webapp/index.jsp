<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<!DOCTYPE html>
<html lang="${sessionScope.lang}">
<head>
    <title>login</title>
    <style>
        body {font-family: Book Antiqua, Helvetica, sans-serif;}
        form {border: 2px solid #f1f1f1; align-self: center}
        input[type=text], input[type=password] {
            width: 25%;
            padding: 10px 10px;
            margin: 10px 0;
            box-sizing: border-box;
            border: 2px solid #ccc;
            box-shadow:0 0 15px 4px rgba(0,0,0,0.05);
            -webkit-transition: 750ms;
            transition: 350ms;
            outline: none;
        }.type-1 {
             border-radius:10px;
             transition: .3s border-color;
         }

        input[type=text]:focus, input[type=password]:focus {
            border: 2px solid #555;
        }

        input[type=submit] {
            background-color: #eecffd;
            color: white;
            padding: 10px 10px;
            margin: 10px 0;
            border: none;
            cursor: pointer;
            width: 25%;
        }.button {
             border-radius:10px;
             transition: .3s border-color;
         }

        input[type=submit]:hover {
            opacity: 0.8;
        }
    </style>
</head>

<jsp:include page="/header.jsp"></jsp:include>

<body>
<div class="form">
    <br>

    <form id="log" method="post" action="<c:url value='/front_controller'/>" style="text-align: center">
        <input type="hidden" name="command" value="utils.Login" />
        <label> <b><fmt:message key="label.form.login" /></b></label><br>
        <input type="text" required placeholder=<fmt:message key="login.placeholder" />
                name="login", id="login" class="type-1">
        <br>
        <label> <b><fmt:message key="label.password" /></b></label><br>
        <input type="password" required placeholder=<fmt:message key="password.placeholder" />
                name="password", id="password" class="type-1">
        <br>
        <input class="button" type="submit" value="<fmt:message key="button.submit"/>"/>
    </form>


</div>

<form id="registration" method="get" action="<c:url value='/front_controller'/>" style="text-align: center">
    <input type="hidden" name="command" value="utils.DisplayRegistration" /> <input
        type="submit" class="button" value="<fmt:message key="button.registration" />" >
</form>
<br/>

</body>
</html>