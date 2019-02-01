<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form modelAttribute="phase" action="phase/handyworker/save.do">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="application" />

	<form:label path="title">
		<spring:message code="phase.title" />:*</form:label>
	<form:input path="title" />
	<form:errors path="title" cssClass="error" code="phase.error.title" />
	<br />
	<form:label path="description">
		<spring:message code="phase.description" />:*</form:label>
	<form:input path="description" />
	<form:errors path="description" cssClass="error"
		code="phase.error.description" />
	<br />
	<form:label path="startTime">
		<spring:message code="phase.startTime" />:*</form:label>
	<form:input path="startTime" />
	<form:errors path="startTime" cssClass="error"
		code="phase.error.startTime" />
	<jstl:if test="${dateError == 'phase.date.error' }">
		<span class="error"><spring:message code='${dateError }' /></span>
	</jstl:if>
	<br />
	<form:label path="endTime">
		<spring:message code="phase.endTime" />:*</form:label>
	<form:input path="endTime" />
	<form:errors path="endTime" cssClass="error" code="phase.error.endTime" />
	<br />
	<div><spring:message code='mandatory' /></div>
	<input type="submit" name="save"
		value="<spring:message code="box.save"/>" />

</form:form>
