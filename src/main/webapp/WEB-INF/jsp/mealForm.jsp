<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<br>
<section>
    <h2><spring:message code="${meal.id == null ? 'meal.form.create' : 'meal.form.update'}"/></h2>
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
    <form method="post" action="${pageContext.request.contextPath}meals">
        <input type="hidden" name="id" value="${meal.id}">
        <dl>
            <dt><spring:message code="meal.form.date_time"/></dt>
            <dd><label>
                <input type="datetime-local" value="${meal.dateTime}" name="dateTime" required>
            </label></dd>
        </dl>
        <dl>
            <dt><spring:message code="meal.form.description"/></dt>
            <dd><label>
                <input type="text" value="${meal.description}" size=40 name="description" required>
            </label></dd>
        </dl>
        <dl>
            <dt><spring:message code="meal.form.calories"/></dt>
            <dd><label>
                <input type="number" value="${meal.calories}" name="calories" required>
            </label></dd>
        </dl>
        <button type="submit"><spring:message code="common.save"/></button>
        <button onclick="window.history.back()" type="button"><spring:message code="common.cancel"/></button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
