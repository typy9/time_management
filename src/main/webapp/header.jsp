<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tf" tagdir="/WEB-INF/tags" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<!DOCTYPE html>
<html lang="${sessionScope.lang}">

<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>
        * {box-sizing: border-box;}

        body {
            margin: 0;
            font-family: Book Antiqua, Helvetica, sans-serif;
        }

        .header {
            overflow: hidden;
            background-color: #eecffd;
            padding: 20px 10px;
        }

        .header a {
            float: left;
            color: black;
            text-align: center;
            padding: 12px;
            text-decoration: none;
            font-size: 18px;
            line-height: 25px;
            border-radius: 4px;
        }

        .header a:hover {
            background-color: #51c6f8;
            color: black;
        }
        .header-right {
            float: right;
        }

        @media screen and (max-width: 500px) {
            .header a {
                float: none;
                display: block;
                text-align: left;
            }
            .header-right {
                float: none;
            }
        }
    </style>
</head>

<body>
<div class="header">
    <a href="${pageContext.request.contextPath}/" class="logo"><fmt:message key="label.header.name" /></a>
    <div class="header-right">
        <a> <tf:date_time/> </a>
        <a href="?sessionLocale=en" ><fmt:message key="label.lang.en" /></a>
        <a href="?sessionLocale=ukr" ><fmt:message key="label.lang.ukr" /></a>
    </div>
</div>

</body>
</html>