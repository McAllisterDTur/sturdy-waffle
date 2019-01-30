<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	String s = request.getUserPrincipal() != null ? request.getUserPrincipal().getName() : "";
%>
<jstl:set var="principal" value="<%=s%>" />

<h3>
	<b><spring:message code="message.subject" />: </b>
	<jstl:out value="${messageO.subject}" />
</h3>
<jstl:if test="${not empty messageCode}">
	<h4>
		<spring:message code="${messageCode}" />
	</h4>
</jstl:if>

<b><spring:message code="message.actor.sender" />: </b>
<c:choose>
	<c:when test="${fn:length(messageO.reciever) eq 1}">
		<jstl:forEach var="actor" items="${messageO.reciever}">
			<jstl:out value="${actor.account.username}"></jstl:out>
		</jstl:forEach>
	</c:when>
	<c:otherwise>
		<jstl:out value="${messageO.sender.account.username}" />
		<br />
	</c:otherwise>
</c:choose>
<b><spring:message code="message.actor.reciever" />: </b>
<c:choose>
	<c:when test="${fn:length(messageO.reciever) eq 1}">
		<jstl:forEach var="actor" items="${messageO.reciever}">
			<jstl:out value="${actor.account.username}"></jstl:out>
		</jstl:forEach>
	</c:when>
	<c:otherwise>
		<spring:message code="message.broadcast" />
	</c:otherwise>
</c:choose>
<br />

<b><spring:message code="message.sendTime" />: </b>
<jstl:out value="${messageO.sendTime}" />
<br />

<b><spring:message code="message.priority" />: </b>
<jstl:out value="${messageO.priority}" />
<br />

<b><spring:message code="message.tags" />: </b>
<jstl:out value="${messageO.tags}" />
<br />

<b><spring:message code="message.body" />: </b>
<jstl:out value="${messageO.body}" />
<br />

<button onClick="window.location.href='box/list.do'">
	<spring:message code="message.cancel" />
</button>
