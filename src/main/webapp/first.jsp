<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored = "false" %>
<html>
<head>
    <title>Main page</title>
</head>
<body>
${users}
    <form action="registration" method="post">
        <input type="text" name="username" placeholder="Insert your username"/>
        <input type="password" name="password" placeholder="Insert your password" />
        <input type="submit" name="submit"/>
    </form>
</body>
</html>
