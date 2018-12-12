<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<div class="formulario">
<form:form modelAtribute="application">
	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<form:label path="maxPrice">
		<spring:message code="handy.application.maxPrice" />
	</form:label>
	<form:input path="maxPrice"/>
	<form:errors path="maxPrice" cssClass="error"/>
	
	<br />
	
	<input type="submit" value="<spring:message code="handy.application.create" />" />
 </form:form>
 
 </div>
 <div class="taskToAplly">
 <!-- TODO -->
 </div>