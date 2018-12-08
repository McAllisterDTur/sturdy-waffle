<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<h1><spring:message code="messages.newt"/></h1>
<div>
	<form:form>
		<p><spring:message code="messages.mgto"/>: <form:input path="to"/></p>
		<p><spring:message code="messages.sbjc"/>: <form:input path="subject"/></p>
		<p><spring:message code="messages.msgt"/></p>
		<form:textarea path="messageText" placeholder="<spring:message code="messages.wrth"/>"/>
		<p><spring:message code="messages.tags"/>: <form:input path="tags"/></p>
		<input type="submit" name="send" value="<spring:message code="messages.send"/>"/>
	</form:form>
</div>
