<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Edit meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Edit meal</h2>

<jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal"/>
<form  method="post" action="meals?action=submit">
    <input type="hidden" name="id" value="${meal.id}" placeholder="${meal.id}"/>
    <table width="30%" cellspacing="0" cellpadding="4">
        <tr>
            <td><label><input type="hidden" name="id" value="${meal.id}" /></label></td>
        </tr>
        <tr>
            <td>DateTime:</td>
            <td><label><input type="datetime-local" name="dateTime" value="${meal.dateTime}" placeholder="${meal.dateTime}"/></label></td>
        </tr>
        <tr>
            <td>Description:</td>
            <td><label><input type="text" name="description" value="${meal.description}" placeholder="${meal.description}"/></label></td>
        </tr>
        <tr>
            <td>Calories:</td>
            <td><label><input type="number" name="calories" value="${meal.calories}" placeholder="${meal.calories}"/></label></td>
        </tr>
        <tr>
            <td paddng = "5">
                <button type="submit">Save</button>
                <button type="button" onclick="window.history.back()">Cancel</button>
            </td>
        </tr>
    </table>
</form>
</body>
</html>
