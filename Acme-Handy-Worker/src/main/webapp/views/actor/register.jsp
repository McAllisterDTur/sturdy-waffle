<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<form:form modelAttribute="actor" action="${uri}">
	<form:hidden path="id" />
	<form:hidden path="version"/>
	<form:hidden path="banned" />
	
	<!-- Account Atributtes -->
	<form:label path="account.username">
		<spring:message code="actor.userAccount.name" />*
	</form:label>

	<form:input path="account.username" />
	<form:errors cssClass="error" path="account.username" />

	<br />

	<form:label path="account.password">
		<spring:message code="actor.userAccount.pass" />*
	</form:label>


	<form:input type="password" path="account.password" />
	<form:errors cssClass="error" path="account.password" />

	<br />
	<!-- Account Authority -->
	<form:label path="account.authorities">
		<spring:message code="actor.authority" />*
	</form:label>

	<jstl:forEach var="authority" items="${authorities}">
		<form:radiobutton path="account.authorities" label="${authority}" value="${authority}"/>
	</jstl:forEach>
	<form:errors cssClass="error" path="account.authorities" />


	<br />
	<form:label path="name">
		<spring:message code="actor.name" />*
	</form:label>
	<form:input path="name" />
	<form:errors cssClass="error" path="name" />

	<br />
	<form:label path="middleName">
		<spring:message code="actor.middlename" />
	</form:label>
	<form:input path="middleName" />
	<form:errors cssClass="error" path="middleName" />

	<br />
	<form:label path="surname">
		<spring:message code="actor.surname" />*
	</form:label>
	<form:input path="surname" />
	<form:errors cssClass="error" path="surname" />

	<br />
	<form:label path="photoURL">
		<spring:message code="actor.photo" />
	</form:label>
	<form:input path="photoURL" />
	<form:errors cssClass="error" path="photoURL" />

	<br />
	<form:label path="email">
		<spring:message code="actor.email" />*
	</form:label>
	<form:input path="email" />
	<form:errors cssClass="error" path="email" />

	<br />
	<form:label path="phone">
		<spring:message code="actor.phone" />*
	</form:label>
	<form:input path="phone" placeholder="+CC (AC) 666 333 222" />
	<form:errors cssClass="error" path="phone" />

	<br />
	<form:label path="address">
		<spring:message code="actor.address" />
	</form:label>
	<form:input path="address" />
	<form:errors cssClass="error" path="address" />

	<input type="submit" value="<spring:message code="actor.submit" />" />

</form:form>