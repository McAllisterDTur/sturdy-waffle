<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<script type='text/javascript'>
	function addFields() {
		// Container <div> where dynamic content will be placed
		var container = document.getElementById("container");
		// Create an <input> element, set its type and name attributes
		var input = document.createElement("input");
		input.type = "text";
		input.name = "tags";
		container.appendChild(input);
	}
</script>

<%
	String s = request.getUserPrincipal() != null ? request.getUserPrincipal().getName() : "";
%>
<jstl:set var="principal" value="<%=s%>" />

<jstl:if test="${not empty messageCode}">
	<h4>
		<spring:message code="${messageCode}" />
	</h4>
</jstl:if>
<form:form action="message/administrator/broadcast.do"
	modelAttribute="messageO">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="sender" />
	<form:hidden path="sendTime" />
	<form:hidden path="reciever" />
	<form:hidden path="boxes" />


	<h3>
		<spring:message code="message.broadcast" />
	</h3>

	<form:label path="subject">
		<spring:message code="message.subject" />*:</form:label>
	<form:input path="subject" />
	<form:errors path="subject" />
	<br />

	<form:label path="priority">
		<spring:message code="message.priority" />*:</form:label>
	<form:select path="priority">
		<form:option value="HIGH" />
		<form:option value="NEUTRAL" />
		<form:option value="LOW" />
	</form:select>
	<form:errors path="priority" />
	<br />

	<spring:message code="message.tags" />
		:
		<button type="button" onClick="addFields()">
		<spring:message code="message.tags" />
	</button>
	<div id="container"></div>
	<jstl:forEach items="${messageO.tags}" var="tag">
		<input name=tags value="${tag}" />
	</jstl:forEach>
	<form:errors path="tags" cssClass="error" />

	<form:label path="body">
		<spring:message code="message.body" />*: </form:label>
	<form:textarea path="body" />
	<form:errors path="body" />
	<br />
	<spring:message code="mandatory" />
	<br />

	<input type="submit" name="save"
		value="<spring:message code="message.save"/>" />

</form:form>
