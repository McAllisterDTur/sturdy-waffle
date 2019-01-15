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

<script>
	function checkPhone(errorCode) {

		var pattern = /((([+][1-9]{1}[0-9]{0,2}[\s]){0,1}([(][1-9]{1}[0-9]{0,2}[)][\s]){0,1})){0,1}([0-9]{4}){1}([0-9]{0,})/;
		var phone = document.getElementById("phone").value;
		var error = !pattern.test(phone);
		if (error){
			return confirm(errorCode);			
		}
		return;
	}
</script>
<h2><spring:message code="profile.edit.info" /></h2>
<spring:message code="actor.phone.error" var="phoneError"/>

<jstl:if test="${not empty success }">
	<fieldset>
		<legend><spring:message code="profile.message"/></legend>
		<jstl:if test="${success }">
			<spring:message code="profile.edit.success"/>
		</jstl:if>
		<jstl:if test="${not success }">
			<spring:message code="profile.edit.error"/>
		</jstl:if>
	</fieldset>
</jstl:if>

<jstl:if test="${not handy }">
	<form:form modelAttribute="actor" action="profile/edit.do" onsubmit="return checkPhone('${phoneError }');" method="POST">
		<p>
			<spring:message code="profile.edit.name"/>: 
			<form:input path="name"/>
			<form:errors path="name" cssClass="error"/>
		</p>
		<p>
			<spring:message code="profile.edit.middleName"/>: 
			<form:input path="middleName"/>
			<form:errors path="middleName" cssClass="error"/>
		</p>
		<p>
			<spring:message code="profile.edit.surname"/>: 
			<form:input path="surname"/>
			<form:errors path="surname" cssClass="error"/>	
		</p>
		<p>
			<spring:message code="profile.edit.photoURL"/>: 
			<form:input path="photoURL"/>
			<form:errors path="photoURL" cssClass="error"/>	
		</p>
		<p>
			<spring:message code="profile.edit.email"/>: 
			<form:input path="email"/>
			<form:errors path="email" cssClass="error"/>	
		</p>
		<p>
			<spring:message code="profile.edit.phone"/>: 
			<form:input path="phone"/>
			<form:errors path="phone" cssClass="error"/>
		</p>
		<p>
			<spring:message code="profile.edit.address"/>: 
			<form:input path="address"/>
			<form:errors path="address" cssClass="error"/>
		</p>
		<form:hidden path="banned" />
		<form:hidden path="account" />
		<form:hidden path="isSuspicious" />
		<input type="submit" name="save" value="<spring:message code="profile.edit.save"/>"/>
	</form:form>
	<br/>
	<button onClick="window.location.href='profile/social/edit.do'">
		<spring:message code="profile.social.edit"/>
	</button>
		
</jstl:if>

<jstl:if test="${handy }">
<form:form modelAttribute="worker" action="profile/edit.do" method="POST">
		<p>
			<spring:message code="profile.edit.name"/>: 
			<form:input path="name"/>
			<form:errors path="name" cssClass="error"/>
		</p>
		<p>
			<spring:message code="profile.edit.make"/>: 
			<form:input path="make"/>
			<form:errors path="make" cssClass="error"/>
		</p>
		<p>
			<spring:message code="profile.edit.middleName"/>: 
			<form:input path="middleName"/>
			<form:errors path="middleName" cssClass="error"/>
		</p>
		<p>
			<spring:message code="profile.edit.surname"/>: 
			<form:input path="surname"/>
			<form:errors path="surname" cssClass="error"/>	
		</p>
		<p>
			<spring:message code="profile.edit.photoURL"/>: 
			<form:input path="photoURL"/>
			<form:errors path="photoURL" cssClass="error"/>	
		</p>
		<p>
			<spring:message code="profile.edit.email"/>: 
			<form:input path="email"/>
			<form:errors path="email" cssClass="error"/>	
		</p>
		<p>
			<spring:message code="profile.edit.phone"/>: 
			<form:input path="phone"/>
			<form:errors path="phone" cssClass="error"/>
		</p>
		<p>
			<spring:message code="profile.edit.address"/>: 
			<form:input path="address"/>
			<form:errors path="address" cssClass="error"/>
		</p>
	<br/>

	<form:hidden path="banned" />
	<form:hidden path="account" />
	<form:hidden path="isSuspicious" />
	<form:hidden path="score" />
	
	<input type="submit" name="saveHandy" value="<spring:message code="profile.edit.save"/>"/>
</form:form>

	<button onClick="window.location.href='profile/social/edit.do'">
		<spring:message code="profile.social.edit"/>
	</button>
</jstl:if>