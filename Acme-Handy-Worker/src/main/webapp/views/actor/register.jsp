<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <jstl:set var="Authorities" value="${ Authority.listAuthorities() }" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Register</title>
</head>
<body>
	<form:form action="" modelAtribute="newUser">
	
		<!-- hidden atributes -->
		<form:hidden path="id" />
		<form:hidden path="version" />
	
		<!-- Account Atributtes -->
		<form:label path="user_name">
			<spring:message code="actor.userAccount.name" />
		</form:label>
	
		<form:input path="user_name" />
		<form:errors cssClass="error" path="user_name" />
	
		<br />
	
		<form:label path="user_password">
			<spring:message code="actor.userAccount.pass" />
		</form:label>
	
		<form:input path="user_password" />
		<form:errors cssClass="error" path="user_password" />
		
		<br />
		<!-- Account Authority -->
		<form:label path="accountAuthority" />
		<form:select path="accountAuthority" >
			<formt:option
			items="Authorities"
			itemLabel="name"
			itemValue="name" />
			<form:option
			itemLabel="---"
			itemValue=0
			/>
		</form:select>
		
		<br />
		<form:label path="name">
			<spring:message code="actor.name" />
		</form:label>
		<form:input path="name" />
		<form:errors cssClass="error" path="name" />
		
		<br />
		<form:label path="surname">
			<spring:message code="actor.surname" />
		</form:label>
		<form:input path="surname" />
		<form:errors cssClass="error" path="surname" />
		
		<br />
		<form:label path="middlename">
			<spring:message code="actor.middlename" />
		</form:label>
		<form:input path="middlename" />
		<form:errors cssClass="error" path="middlename" />
		
		<br />
		<form:label path="photo">
			<spring:message code="actor.photo" />
		</form:label>
		<form:input path="photo" />
		<form:errors cssClass="error" path="photo" />
		
		<br />
		<form:label path="email">
			<spring:message code="actor.email" />
		</form:label>
		<form:input path="email" />
		<form:errors cssClass="error" path="email" />
		
		<br />
		<form:label path="phone">
			<spring:message code="actor.phone" />
		</form:label>
		<form:input path="phone" />
		<form:errors cssClass="error" path="phone" />
		
		<br />
		<form:label path="address">
			<spring:message code="actor.address" />
		</form:label>
		<form:input path="address" />
		<form:errors cssClass="error" path="address" />
		
		<!-- TODO: hidden banned and isSuspicious -->
		<form:input type="submit" name="save" value="<spring:message code="register.save" />" />
	</form:form>
</body>
</html>