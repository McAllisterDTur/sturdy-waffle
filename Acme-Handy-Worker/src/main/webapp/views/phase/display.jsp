<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<div>
	<strong><spring:message code="phase.title" />: </strong><jstl:out value="${ phase.title}"></jstl:out>
	<br />
	<strong><spring:message code="phase.description" />: </strong><jstl:out value="${ phase.description}"></jstl:out>
	<br />
	<strong><spring:message code="phase.startTime" />: </strong><jstl:out value="${ phase.startTime}"></jstl:out>
	<strong><spring:message code="phase.endTime" />: </strong><jstl:out value="${ phase.endTime}"></jstl:out>
	<br />
	<a href="phase/handyworker/edit.do?phaseId=${ phase.id}"><spring:message code="phase.edit" /></a>
	<a href="phase/handyworker/delete.do?phaseId=${ phase.id}"><spring:message code="phase.delete" /></a>
</div>