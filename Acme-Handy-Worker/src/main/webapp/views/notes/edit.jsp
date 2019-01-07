<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<script type='text/javascript'>
        function addFields(){
	        // Container <div> where dynamic content will be placed
	        var container = document.getElementById("container");
	        // Create an <input> element, set its type and name attributes
	        var input = document.createElement("input");
	        input.type = "text";
	        input.name = "comment";
	        container.appendChild(input);       
        }
    </script>
<%
	String s = request.getUserPrincipal() != null ? request.getUserPrincipal().getName() : "";
%>
<jstl:set var="principal" value="<%=s%>" />

<form:form action="notes/edit.do" modelAttribute="note">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="isFinal" />
	<form:hidden path="report" />
	<form:hidden path="moment" />
	<form:hidden path="customerComments" />
	<form:hidden path="refereeComments" />
	<form:hidden path="handyComments" />

	<h3>
		<spring:message code="notes.header" />
	</h3>

	<security:authorize access="hasRole('CUSTOMER')">

		<form:label path="customerComments">
			<spring:message code="notes.write.comment" />:</form:label>
		<form:textarea path="customerComments" />
		<form:errors path="customerComments" />
		<br />
		<form:hidden path="refereeComments" />
		<form:hidden path="handyComments" />
	</security:authorize>

	<security:authorize access="hasRole('REFEREE')">
		<form:label path="refereeComments">
			<spring:message code="notes.write.comment" />:</form:label>
		<form:textarea path="refereeComments" />
		<form:errors path="refereeComments" />
		<br />
		<form:hidden path="customerComments" />
		<form:hidden path="handyComments" />
	</security:authorize>

	<security:authorize access="hasRole('HANDYWORKER')">
		<p>
			<spring:message code="notes.write.comment" />
			:
			<button type="button" onClick="addFields()">
				<spring:message code="notes.write.comment" />
			</button>
			<div id="container"></div>

		<jstl:forEach items="${note.handyComments}" var="comment">
				<input name="handyComments" value="${comment}" />
			</jstl:forEach>
			<form:errors path="handyComments" cssClass="error" />
		</p>
<%-- 		<form:label path="handyComments">
				<spring:message code="notes.write.comment" />:</form:label>
			<form:textarea path="handyComments" />
			<form:errors path="handyComments" />
			<br />
			<form:hidden path="refereeComments" />
			<form:hidden path="customerComments" /> --%>
		</security:authorize>

	<input type="submit" name="save"
		value="<spring:message code="notes.save"/>" />
</form:form>
