<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored = "false" %>
<html>
<head>
    <title>Main page</title>
</head>
<body>
${sessionCounter}
Hello, ${sessionScope.username}!
    <a href="test">To test</a>
    <a href="registration">Go to submit page</a>
    <a href="login">Go to login page</a>
</body>
</html>
