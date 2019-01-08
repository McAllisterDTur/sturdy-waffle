<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<% String s = request.getUserPrincipal() != null ? request.getUserPrincipal().getName() :"";%>
<jstl:set var="principal" value="<%= s %>"/>

<form:form action="application/handyworker/save.do" modelAttribute="application">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="fixUpTask"/>
	<form:hidden path="handyWorker" />
	
	<form:hidden path="registerTime" />
	<form:hidden path="status" />
	
	<form:label path="offeredPrice">
		<spring:message code="application.offeredPrice" />
	</form:label>
	<form:input path="offeredPrice"/>
	<form:errors cssClass="error" code="application.error.offeredPrice"/>
	<br />
	<input type="submit" />
</form:form>