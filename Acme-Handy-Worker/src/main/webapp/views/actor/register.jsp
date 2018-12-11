<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<form:form modelAttribute="actor">
	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<!-- Account Atributtes -->
	<form:label path="account.username">
		<spring:message code="actor.userAccount.name" />
	</form:label>
	
	<form:input path="account.username" />
	<form:errors cssClass="error" path="account.username" />
	
	<br />
	
	<form:label path="account.password">
		<spring:message code="actor.userAccount.pass" />
	</form:label>
	
	
	<form:input path="account.password" />
	<form:errors cssClass="error" path="account.password" />
	
	<br />
		<!-- Account Authority -->
		<form:label path="account.authorities" />
		<form:select path="account.authorities" >
			<form:options
			items="${authorities}"
			itemLabel="authority"
			itemValue="authority" />
			<form:option
			value="0"
			label="---"
			/>
		</form:select>
	
</form:form>