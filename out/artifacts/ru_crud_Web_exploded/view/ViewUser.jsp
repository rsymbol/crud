<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title></title>
    <h1>Base users (поиск, стили и коменты добавлю в ближайшие день-два)</h1>
</head>
<body>

<form action="${pageContext.servletContext.contextPath}/user/view" method="POST">
    <table>
        <tr>
            <td width="100px">Name:</td>
            <td><input type="text" name="name" value="${val_cr1}"></td>
            <td><font color="red">${mes_cr1}</font></td>
        </tr>
        <tr>
            <td width="100px">Age:</td>
            <td><input type="text" name="age" value="${val_cr2}"></td>
            <td><font color="red">${mes_cr2}</font></td>
        </tr>
        <tr>
            <td width="100px">Admin:</td>
            <td>
                <select name="admin">
                    <option ${val_cr3 == true ? 'selected' : ''} value="true">true</option>
                    <option ${val_cr3 == true ? '' : 'selected'} value="false">false</option>
                </select>
            </td>
            <td><font color="red">${mes_cr3}</font></td>
        </tr>
    </table>
    <br>
    <input type="hidden" name="statusForm" value="create"/>
    <input type="submit" align="center" value="Add user"/>
</form>
<br><br>

<a href="${pageContext.servletContext.contextPath}/user/view?page=${page-1}">previus</a>
<span> -- page ${page} -- </span>
<a href="${pageContext.servletContext.contextPath}/user/view?page=${page+1}">next</a>

<%--&lt;%&ndash;TODO&ndash;%&gt;--%>
<%--<span style='padding-left:200px;'>Search: </span>--%>
<%--<select id="sear">--%>
    <%--<option selected value="idSearch">id</option>--%>
    <%--<option value="nameSearch">name</option>--%>
    <%--<option value="ageSearch">age</option>--%>
    <%--<option value="isAdminSearch">admin</option>--%>
    <%--<option value="createdDateSearch">date</option>--%>
<%--</select>--%>
<%--<input type="text" name="searchText">--%>
<%--<a href="${pageContext.servletContext.contextPath}/user/view?page=1&fieldSearch="{sss}>search</a>--%>



<br><br>
<table border="1">
    <tr>
        <td width="100px" align="center" style="font-weight:bold"><a
                href="${pageContext.servletContext.contextPath}/user/view?page=1&sort=id">id</a></td>
        <td width="100px" align="center" style="font-weight:bold"><a
                href="${pageContext.servletContext.contextPath}/user/view?page=1&sort=name">Name</a></td>
        <td width="100px" align="center" style="font-weight:bold"><a
                href="${pageContext.servletContext.contextPath}/user/view?page=1&sort=age">Age</a></td>
        <td width="100px" align="center" style="font-weight:bold"><a
                href="${pageContext.servletContext.contextPath}/user/view?page=1&sort=isAdmin">Admin</a></td>
        <td width="150px" align="center" style="font-weight:bold"><a
                href="${pageContext.servletContext.contextPath}/user/view?page=1&sort=createdDate">Date</a></td>
        <td width="100px" align="center" style="font-weight:bold">...</td>
    </tr>
    <c:forEach var="user" items="${base}" varStatus="status">
        <tr valign="top">
            <td>${user.id}</td>
            <td>${user.name}</td>
            <td>${user.age}</td>
            <td align="center">${user.admin}</td>
            <td align="center">${user.createdDate}</td>
            <td align="center">
                <a href="${pageContext.servletContext.contextPath}/user/edit?id=${user.id}&page=${page}">edit</a><br>
                <a href="${pageContext.servletContext.contextPath}/user/view?id=${user.id}&statusForm=delete">delete</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>