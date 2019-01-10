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

<form:form action="box/edit.do" modelAttribute="box">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="deleteable" />
	<form:hidden path="owner" />

	<jstl:forEach var="message" items="${box.messages}">
		<form:hidden path="messages" value="${message.id}" />
	</jstl:forEach>

	<h3>
		<spring:message code="box.header" />
	</h3>
	<jstl:if test="${not empty messageCode}">
		<h4>
			<spring:message code="${messageCode}" />
		</h4>
	</jstl:if>
	<spring:message code="box.placeholder.name"
		var="placeholderdescription" />
	<form:label path="name">
		<spring:message code="box.name" />*: </form:label>
	<form:input path="name" placeholder="${placeholderdescription}" />
	<form:errors path="name" cssClass="error" />
	<br />

	<input type="submit" name="save"
		value="<spring:message code="box.save"/>" />

	<jstl:if test="${box.deleteable == true}">
		<button
			onClick="window.location.href='/Acme-Handy-Worker/box/list.do'">
			<spring:message code="box.cancel" />
		</button>
	</jstl:if>

</form:form>
