<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%
	String s = request.getUserPrincipal() != null ? request.getUserPrincipal().getName() : "";
%>
<jstl:set var="principal" value="<%=s%>" />

<b><spring:message code="note.moment" />:&nbsp;</b>
<jstl:out value="${note.moment}" />
<br />
<br />


<jstl:forEach var="hwc" items="${note.handyComments}">
	<b><spring:message code="notes.handyworker" />:&nbsp;</b>
	<jstl:out value="${hwc}" />
	<br />

</jstl:forEach>
<br />

<jstl:forEach var="cc" items="${note.customerComments}">
	<b><spring:message code="notes.customer" />:&nbsp;</b>
	<jstl:out value="${cc}" />
	<br />

</jstl:forEach>
<br />

<jstl:forEach var="rc" items="${note.refereeComments}">
	<b><spring:message code="notes.referee" />:&nbsp;</b>
	<jstl:out value="${rc}" />
	<br />

</jstl:forEach>
<br />

<button
	onClick="window.location.href='/Acme-Handy-Worker/notes/list.do?reportId=${note.report.id}'">
	<spring:message code="notes.cancel" />
</button>


