<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<% String s = request.getUserPrincipal() != null ? request.getUserPrincipal().getName() :""; %>
<jstl:set var="principal" value="<%= s %>"/>

<form:form action="creditCard/customer/save.do?applicationId=${applicationId }" modelAttribute="fixuptask">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="description" />
	<form:hidden path="address" />
	<form:hidden path="maxPrice" />
	<form:hidden path="ticker" />
	<form:hidden path="publishTime" />
	<form:hidden path="periodStart" />
	<form:hidden path="periodEnd" />
	<form:hidden path="category" />
	<form:hidden path="warranty" />
	<form:hidden path="customer" />
	<!-- <input type="hidden" value="${applicationId }" />  -->
	
	<h3><spring:message code="creditCard.header"/> </h3>

	<spring:message code="creditcard.placeholder.holdername" var="placeholderholdername"/>
	<form:label path="creditCard.holderName">
		<spring:message code="creditCard.holderName" />
	</form:label>
	<form:input path="creditCard.holderName" placeholder="${placeholderholdername}" />
	<form:errors path="creditCard.holderName" cssClass="error"/>
	<br />

	<spring:message code="creditcard.placeholder.number" var="placeholdernumber"/>
	<form:label path="creditCard.number">
		<spring:message code="creditCard.number" />
	</form:label>
	<form:input path="creditCard.number" placeholder="${placeholdernumber}" />
	<form:errors path="creditCard.number" cssClass="error" />
	<br />

	<spring:message code="creditcard.placeholder.expirationmonth" var="placeholderexpirationmonth"/>
	<form:label path="creditCard.expirationMonth">
		<spring:message code="creditCard.expirationMonth" />
	</form:label>
	<form:input path="creditCard.expirationMonth" placeholder="${placeholderexpirationmonth}" />
	<form:errors path="creditCard.expirationMonth" cssClass="error" />
	<br />

	<spring:message code="creditcard.placeholder.expirationyear" var="placeholderexpirationyear"/>
	<form:label path="creditCard.expirationYear">
		<spring:message code="creditCard.expirationYear" />
	</form:label>
	<form:input path="creditCard.expirationYear" placeholder="${placeholderexpirationyear}" />
	<form:errors path="creditCard.expirationYear" cssClass="error" />
	<br />

	<form:label path="creditCard.codeCVV">
		<spring:message code="creditCard.codeCVV" />
	</form:label>
	<spring:message code="creditcard.placeholder.codecvv" var="placeholdercodecvv"/>
	<form:input path="creditCard.codeCVV" placeholder="${placeholdercodecvv}" />
	<form:errors path="creditCard.codeCVV" cssClass="error"/>
	<br />

	<form:label path="creditCard.brandName"><spring:message code="configuration.cardMaker" />:</form:label>
	<form:select path="creditCard.brandName">
		<form:options path="creditCard.brandName" items="${makers}"/>
	</form:select>
	<form:errors path="creditCard.brandName" cssClass="error"/>
	<br />

	<input type="submit" name="save" value="<spring:message code="fixuptask.save"/>" />
	<jstl:if test="${fixuptask.customer.account.username == principal}">
		<button onClick="window.location.href='/Acme-Handy-Worker/fixuptask/delete.do?id=${fixuptask.id}'">
			<spring:message code="fixuptask.delete" />
		</button>
	</jstl:if>
</form:form>
