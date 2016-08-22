<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title></title>
    <h1>Base users (EDIT user with ID <%= request.getParameter("id") %>)</h1>
</head>
<body>

<form action="${pageContext.servletContext.contextPath}/user/view" method="POST">
    <table>
        <tr>
            <td width="100px">Name:</td>
            <td><input type="text" name="name" value="${name}"></td>
        </tr>
        <tr>
            <td width="100px">Age:</td>
            <td><input type="text" name="age" value="${age}"></td>
        </tr>
        <tr>
            <td width="100px">Admin:</td>
            <td>
                <select name="admin">
                    <option ${admin == true ? 'selected' : ''} value="true">true</option>
                    <option ${admin == true ? '' : 'selected'} value="false">false</option>
                </select>
            </td>
        </tr>
    </table>
    <br>
    <input type="hidden" name="id" value="<%= request.getParameter("id") %>"/>
    <input type="hidden" name="statusForm" value="edit"/>
    <input type="hidden" name="page" value="<%= request.getParameter("page") %>"/>
    <input type="submit" align="center" value="Edit user"/>
</form>
<br>
<form action="${pageContext.servletContext.contextPath}/user/view" method="GET">
    <input type="submit" align="center" value="Cancel"/>
</form>

</body>
</html>