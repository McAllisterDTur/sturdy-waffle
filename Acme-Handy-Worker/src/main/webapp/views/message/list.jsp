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

<display:table name="messages" id="row" pagesize="5"
	requestURI="${requestURI}">

	<display:column property="subject" titleKey="message.subject" />

	<display:column property="reciever.account.username"
		titleKey="message.actor.reciever" />

	<display:column property="sendTime" titleKey="message.sendTime" />

	<display:column property="priority" titleKey="message.priority" />

	<display:column property="tags" titleKey="message.tags" />

	<display:column>
		<button
			onClick="window.location.href='/Acme-Handy-Worker/message/display.do?messageId=${row.id}'">
			<spring:message code="message.display" />
		</button>
	</display:column>
		<display:column>
		<button
			onClick="window.location.href='/Acme-Handy-Worker/message/copy.do?messageId=${row.id}'">
			<spring:message code="message.edit" />
		</button>
	</display:column>
	<display:column>
		<button
			onClick="window.location.href='/Acme-Handy-Worker/message/delete.do?messageId=${row.id}&boxId=${box.id}'">
			<spring:message code="message.delete" />
		</button>
	</display:column>

</display:table>
