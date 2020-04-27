<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored = "false" %>
<html>
<head>
    <title>Main page</title>
</head>
<body>
${sessionCounter}
<c:if test="${sessionScope.user != null}">
    Hello, ${sessionScope.user.username}!
    <a href="${pageContext.request.contextPath}/cabinet?username=${sessionScope.user.username}"
</c:if>
<%
    String strings = "first, second, third";
    request.setAttribute("strings", strings);
%>
<c:forTokens items="${strings}" delims="," var="number">
    ${number}
</c:forTokens>
    <a href="test">To test</a>
    <a href="registration">Go to submit page</a>
    <a href="login">Go to login page</a>
</body>
</html>
