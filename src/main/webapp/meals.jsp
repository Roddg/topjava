<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="en">
<head>
    <title>Meals</title>
    <style type="text/css">
        table {
            border-collapse: collapse;
        }
        td {
            padding: 10px;
            border: 2px solid black;
        }
    </style>
</head>
<body>
<h3>
    <a href="index.html">Home</a>
</h3>
<hr>
<h2>Meals</h2>
<h4>
    <a href="meals?action=create">Add Meal</a>
</h4>
<table>
    <thead>
    <tr align="center" strong>
        <td><strong>Date</strong></td>
        <td><strong>Description</strong></td>
        <td><strong>Calories</strong></td>
        <td></td>
        <td></td>
    </tr>
    </thead>
    <jsp:useBean id="dateTimeFormatter" scope="request" type="java.time.format.DateTimeFormatter"/>
    <jsp:useBean id="meals" scope="request" type="java.util.List<ru.javawebinar.topjava.model.MealTo>"/>
    <c:forEach var="mealTo" items="${meals}">
        <jsp:useBean id="mealTo" type="ru.javawebinar.topjava.model.MealTo" />
        <tr style="color: ${mealTo.excess ? "red" : "green"}">
            <td>${dateTimeFormatter.format(mealTo.dateTime)}</td>
            <td>${mealTo.description}</td>
            <td>${mealTo.calories}</td>
            <td><a href="meals?action=update&id=${mealTo.id}">Update</a></td>
            <td><a href="meals?action=delete&id=${mealTo.id}">Delete</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
