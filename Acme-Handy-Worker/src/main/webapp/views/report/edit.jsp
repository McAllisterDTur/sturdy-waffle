<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="report/referee/save.do" modelAttribute="report">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="reportTime"/>
	<form:hidden path="isFinal"/>
	<form:hidden path="complaint" />

	<form:label path="description"><spring:message code="report.description" />:</form:label>
	<br />
	<form:textarea path="description"/>
	<form:errors path="description"/>
	<br />

	<input type="submit" name="save" value="<spring:message code="report.save"/>" />
</form:form>
