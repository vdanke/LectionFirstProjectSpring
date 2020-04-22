<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored = "false" %>
<html>
<head>
    <title>Main page</title>
</head>
<body>
${users}
    <form action="save" method="post">
        <input type="hidden" name="username" value="${username}" />
        <input type="text" name="phone" placeholder="insert your phone"/>
        <input type="submit" name="submit"/>
    </form>
</body>
</html>
