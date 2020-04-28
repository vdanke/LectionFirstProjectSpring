<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored = "false" %>
<html>
<head>
    <title>Main page</title>
</head>
<body>
${sessionCounter}
<c:if test="${error != null}">
    Error is happened ${error}
</c:if>
<c:if test="${sessionScope.user.role != null && sessionScope.user.role.toString().equals('ROLE_ADMIN')}">
    <a href="${pageContext.request.contextPath}/users">Users</a>
</c:if>
<c:choose>
    <c:when test="${sessionScope.user != null}">
        Hello, ${sessionScope.user.username}!
        <a href="${pageContext.request.contextPath}/cabinet?username=${sessionScope.user.username}">Cabinet</a>
        <a href="${pageContext.request.contextPath}/logout">Logout</a>
    </c:when>
    <c:otherwise>
        <a href="test">To test</a>
        <a href="registration">Go to submit page</a>
        <a href="login">Go to login page</a>
    </c:otherwise>
</c:choose>
</body>
</html>
