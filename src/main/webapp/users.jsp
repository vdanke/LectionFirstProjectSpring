<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: vielen
  Date: 4/28/20
  Time: 4:54 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Users</title>
</head>
<body>
<a href="index.jsp">Main page</a>
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
</body>
</html>
