<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<form:form modelAttribute="tutorial" action="tutorial/new.do" method="post">
	<form:hidden path="id"/>
	<form:hidden path="version"/>

	<form:label path="title"><spring:message code="tutorial.title" />:</form:label>
	<form:input path="title" />
	<form:errors path="title" />
	
	<br/>
	
	<form:label path="summary"><spring:message code="tutorial.summary" />:</form:label>
	<form:input path="summary" />
	<form:errors path="summary" />
	
	<br/>
	
	<jstl:forEach var="picture" items="${tutorial.photoURL}"> <img src="${picture}" alt="Smiley face" height="200"> </jstl:forEach>
	
	<input type="submit" name="save" value="Submit" />
</form:form>