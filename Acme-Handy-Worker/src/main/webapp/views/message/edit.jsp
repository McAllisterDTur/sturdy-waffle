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

<form:form action="message/edit.do" modelAttribute="messageO">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="sender" />
	<form:hidden path="sendTime" />

	<jstl:forEach var="box" items="${message.boxes}">
		<form:hidden path="boxes" value="${box.id}" />
	</jstl:forEach>

	<h3>
		<spring:message code="message.create" />
	</h3>

	<form:label path="subject">
		<spring:message code="message.subject" />:</form:label>
	<form:input path="subject" placeholder="${placeholderdescription}" />
	<form:errors path="subject" />
	<br />

	<b><form:label path="reciever">
			<spring:message code="message.actor.reciever" />:&nbsp;</form:label></b>
	<form:select path="reciever">
		<form:option label="----" value="0" />
		<jstl:forEach var="act" items="${actors}">
			<form:option value="${act.id}"  label="${act.account.username}"/>
		</jstl:forEach>
	</form:select>
	<form:errors path="reciever" />
	<br />

	<form:label path="priority">
		<spring:message code="message.priority" />:</form:label>
	<form:select path="priority">
		<form:option value="HIGH" />
		<form:option value="NEUTRAL" />
		<form:option value="LOW" />
	</form:select>
	<form:errors path="priority" />
	<br />

	<form:label path="tags">
		<spring:message code="message.tags" />:</form:label>
	<form:input path="tags" />
	<form:errors path="tags" />
	<br />

	<form:label path="body">
		<spring:message code="message.body" />:</form:label>
	<form:input path="body" />
	<form:errors path="body" />
	<br />

	<jstl:if test="${messageO.sender.account.username == principal}">
		<button
			onClick="window.location.href='/Acme-Handy-Worker/message/save.do?id=${messageO.id}'">
			<spring:message code="message.save" />
		</button>
	</jstl:if>

	<jstl:if test="${message.sender.account.username == principal}">
		<button
			onClick="window.location.href='/Acme-Handy-Worker/box/list.do'">
			<spring:message code="message.cancel" />
		</button>
	</jstl:if>

</form:form>
