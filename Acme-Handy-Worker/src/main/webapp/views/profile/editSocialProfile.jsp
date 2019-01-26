<%--
 * action-1.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


	<form:form modelAttribute="socialProfile" action="profile/social/save.do" method="POST">
		<p>
			<spring:message code="profile.social.network"/>*: 
			<form:input path="socialNetwork"/>
			<form:errors path="socialNetwork" cssClass="error"/>
		</p>
		<p>
			<spring:message code="profile.social.nick"/>*: 
			<form:input path="nick"/>
			<form:errors path="nick" cssClass="error"/>
		</p>
		<p>
			<spring:message code="profile.social.link"/>*: 
			<form:input path="profileLink" value="http://"/>
			<form:errors path="profileLink" cssClass="error"/>	
		</p>
		<p><b><spring:message code="profile.mandatory"/></b></p>
		<input type="submit" name="save" value="<spring:message code="profile.edit.save"/>"/>
	</form:form>	
