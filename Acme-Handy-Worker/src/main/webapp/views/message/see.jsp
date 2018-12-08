<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<div>
	<p><spring:message code="messages.msgt"/></p>
	<p><jstl:out value="${message.text}"/></p>
</div>
<div>
	<p>
		<spring:message code="messages.sent"/>
		<jstl:out value="${message.author}"/>
	</p>
	<p>
		<spring:message code="messages.date"/>
		<jstl:out value="${message.dateSent}"/>
	</p>
	<p>
		<spring:message code="messages.tags"/>
		<jstl:out value="${message.tags}"/>
	</p>
</div>
