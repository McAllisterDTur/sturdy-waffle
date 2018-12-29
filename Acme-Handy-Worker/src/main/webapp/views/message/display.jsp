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

<h3>
	<b><spring:message code="message.subject" />:&nbsp;</b>
	<jstl:out value="${messageO.subject}" />
</h3>

<b><spring:message code="message.actor.sender" />:&nbsp;</b>
<jstl:out value="${messageO.sender.account.username}" />
<br />

<b><spring:message code="message.actor.reciever" />:&nbsp;</b>
<jstl:out value="${messageO.reciever.account.username}" />
<br />

<b><spring:message code="message.sendTime" />:&nbsp;</b>
<jstl:out value="${messageO.sendTime}" />
<br />

<b><spring:message code="message.priority" />:&nbsp;</b>
<jstl:out value="${messageO.priority}" />
<br />

<b><spring:message code="message.tags" />:&nbsp;</b>
<jstl:out value="${messageO.tags}" />
<br />

<b><spring:message code="message.body" />:&nbsp;</b>
<jstl:out value="${messageO.body}" />
<br />

<button
	onClick="window.location.href='/Acme-Handy-Worker/message/delete.do?messageId=${messageO.id}'">
	<spring:message code="message.delete" />
</button>

<button onClick="window.location.href='/Acme-Handy-Worker/box/list.do'">
	<spring:message code="message.cancel" />
</button>
