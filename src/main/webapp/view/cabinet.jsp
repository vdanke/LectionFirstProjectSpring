<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: vielen
  Date: 4/27/20
  Time: 4:47 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Cabinet</title>
</head>
<body>
<c:choose>
    <c:when test="${sessionScope.user.role.toString().equals('ROLE_USER') || sessionScope.user.role.toString().equals('ROLE_ADMIN')}">
        Everything is fine
        <a href="${pageContext.request.contextPath}/logout">Logout</a>
        <a href="${pageContext.request.contextPath}/">Main page</a>
    </c:when>
    <c:otherwise>
        Something went wrong
    </c:otherwise>
</c:choose>
    <div>
        Your ID is: ${sessionScope.user.id}
    </div>
    <div>
        Your username is: ${sessionScope.user.username}
    </div>
    <div>
        Your password is: ${sessionScope.user.password}
    </div>
<form action="${pageContext.request.contextPath}/update/${sessionScope.user.id}" method="post">
    <input type="text" name="username" placeholder="Update your username"/>
    <input type="password" name="password" placeholder="Update your password"/>
    <input type="submit" value="Update">
</form>
</body>
</html>
