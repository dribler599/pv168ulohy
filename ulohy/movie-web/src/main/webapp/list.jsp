<%--
  Created by IntelliJ IDEA.
  User: Lukas
  Date: 10.4.2017
  Time: 21:17
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body>

<table border="1">
    <thead>
    <tr>
        <th>název</th>
        <th>rok</th>
        <th>klasifikácia</th>
        <th>popis</th>
        <th>lokácia</th>
    </tr>
    </thead>
    <c:forEach items="${movies}" var="movie">
        <tr>
            <td><c:out value="${movie.name}"/></td>
            <td><c:out value="${movie.year}"/></td>
            <td><c:out value="${movie.classification}"/></td>
            <td><c:out value="${movie.description}"/></td>
            <td><c:out value="${movie.location}"/></td>
            <td><form method="post" action="${pageContext.request.contextPath}/movies/delete?id=${movie.id}"
                      style="margin-bottom: 0;"><input type="submit" value="Smazat"></form></td>
        </tr>
    </c:forEach>
</table>

<h2>Zadejte film</h2>
<c:if test="${not empty chyba}">
    <div style="border: solid 1px red; background-color: yellow; padding: 10px">
        <c:out value="${chyba}"/>
    </div>
</c:if>
<form action="${pageContext.request.contextPath}/movies/add" method="post">
    <table>
        <tr>
            <th>název filmu:</th>
            <td><input type="text" name="name" value="<c:out value='${param.name}'/>"/></td>
        </tr>
        <tr>
            <th>rok:</th>
            <td><input type="text" name="year" value="<c:out value='${param.year}'/>"/></td>
        </tr>
        <tr>
            <th>klasifikácia:</th>
            <td><input type="text" name="classification" value="<c:out value='${param.classification}'/>"/></td>
        </tr>
        <tr>
            <th>popis:</th>
            <td><input type="text" name="description" value="<c:out value='${param.description}'/>"/></td>
        </tr>
        <tr>
            <th>lokácia:</th>
            <td><input type="text" name="location" value="<c:out value='${param.location}'/>"/></td>
        </tr>
    </table>
    <input type="Submit" value="Zadat" />
</form>
</body>
</html>