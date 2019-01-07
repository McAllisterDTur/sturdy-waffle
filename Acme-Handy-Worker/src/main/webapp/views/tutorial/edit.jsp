<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form modelAttribute="tutorial" action="tutorial/handyworker/edit.do" method="post">
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="lastTimeUpdated"/>
	
	<jstl:forEach var="url" items="${tutorial.photoURL}">
		<form:hidden path = "photoURL" value = "${url}" />
	</jstl:forEach>
	
	<form:hidden path="worker"/>

	<jstl:forEach var="sponsorship" items="${tutorial.sponsorships}">
		<form:hidden path = "sponsorships" value = "${sponsorship.id}" />
	</jstl:forEach>
	
	<jstl:forEach var="section" items="${tutorial.sections}">
		<form:hidden path = "sections" value = "${section.id}" />
	</jstl:forEach>

	<form:label path="title"><spring:message code="tutorial.title" />:</form:label>
	<form:input path="title" />
	<form:errors path="title" />
	
	<br/>
	
	<form:label path="summary"><spring:message code="tutorial.summary" />:</form:label>
	<form:input path="summary" />
	<form:errors path="summary" />
	
	<br/>
	<input type="submit" name="save" value="Submit" />
</form:form>