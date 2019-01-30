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

<form:form action="application/customer,handyworker/save.do"
	modelAttribute="application">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="fixUpTask" />
	<form:hidden path="handyWorker" />

	<form:hidden path="registerTime" />
	<form:hidden path="status" />
	<form:hidden path="customerComments" />
	<form:hidden path="handyComments" />

	<jstl:if test="${application.id==0}">
		<form:label path="offeredPrice">
			<spring:message code="application.offeredPrice" />
		</form:label>
		<form:input path="offeredPrice" />
		<form:errors path="offeredPrice" cssClass="error"
			code="application.error.offeredPrice" />
		<br />
	</jstl:if>
	<jstl:if test="${application.id != 0 }">
		<form:hidden path="offeredPrice" />
	</jstl:if>


	<security:authorize access="hasRole('HANDYWORKER')">
		<form:label path="handyComments">
			<spring:message code="application.comments" />
		</form:label>
		<textarea name="handyComment" rows="4" cols="50"></textarea>
		<input type="hidden" name="customerComment" value="" />
		<form:errors path="handyComments" cssClass="error" />
	</security:authorize>
	<security:authorize access="hasRole('CUSTOMER')">
		<form:label path="customerComments">
			<spring:message code="application.comments" />
		</form:label>
		<input type="hidden" name="handyComment" value="" />
		<textarea name="customerComment" rows="4" cols="50"></textarea>

		<form:errors path="customerComments" cssClass="error" />
	</security:authorize>
	<br />
	<input type="submit"
		value="<spring:message code="application.addComment" />" />
</form:form>
