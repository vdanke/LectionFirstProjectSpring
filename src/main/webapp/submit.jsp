<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored = "false" %>
<html>
<head>
    <title>Main page</title>
</head>
<body>
<c:if test="${users != null}">
<table>
    <thead>
        <tr>
            <th>ID</th>
            <th>Username</th>
            <th>Password</th>
        </tr>
    </thead>
        <c:forEach items="${users}" var="user">
                <tbody>
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.username}</td>
                        <td>${user.password}</td>
                    </tr>
                </tbody>
        </c:forEach>
</table>
</c:if>
${value}
    <form action="registration" method="post" enctype="multipart/form-data">
        <input type="text" name="username" placeholder="Insert your username"/>
        <input type="password" name="password" placeholder="Insert your password" />
        <input type="file" name="file-name" />
        <input type="submit" name="submit"/>
    </form>
</body>
</html>
