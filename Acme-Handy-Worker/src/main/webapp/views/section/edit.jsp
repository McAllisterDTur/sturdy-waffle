<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<% String s = request.getUserPrincipal() != null ? request.getUserPrincipal().getName() :""; %>
<jstl:set var="principal" value="<%= s %>"/>

<form:form modelAttribute="section" action="section/handyworker/edit.do" method="post">
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="tutorial"/>
	<form:hidden path="number"/>
	
	<jstl:forEach var="url" items="${section.photoURL}">
		<form:hidden path = "photoURL" value = "${url}" />
	</jstl:forEach>
	
	<form:label path="title"><spring:message code="section.title" />:</form:label>
	<form:input path="title" />
	<form:errors path="title" />
	<br>
	<form:label path="text"><spring:message code="section.text" />:</form:label>
	<form:input path="text" />
	<form:errors path="text" />
	<input type="submit" name="save" value="Submit" />
</form:form>