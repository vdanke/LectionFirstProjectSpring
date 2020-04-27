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
<c:if test="${authority != null}">
    Your authority is ${authority}
</c:if>
<c:choose>
    <c:when test="${authority.equals('ROLE_USER')}">
        Everything is fine
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
</body>
</html>
