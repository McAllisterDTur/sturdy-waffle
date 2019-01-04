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

<form:form action="message/copy.do" modelAttribute="messageO">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="sender" />
	<form:hidden path="sendTime" />
	<form:hidden path="boxes" />
	<form:hidden path="subject" />
	<form:hidden path="reciever" />
	<form:hidden path="body" />
	<form:hidden path="priority" />
	<form:hidden path="tags" />
	
	<form:label path="boxes">
		<spring:message code="message.list.boxes" />:&nbsp;</form:label>
	<form:select path="boxes">
		<jstl:forEach var="box" items="${boxes}">
			<form:option value="${box.id}" label="${box.name}"></form:option>
		</jstl:forEach>
	</form:select>
	<form:errors path="boxes" />

	<br />
	<input type="submit" name="save"
		value="<spring:message code="message.copy"/>" />
</form:form>
<button onClick="window.location.href='/Acme-Handy-Worker/box/list.do'">
	<spring:message code="message.cancel" />
</button>