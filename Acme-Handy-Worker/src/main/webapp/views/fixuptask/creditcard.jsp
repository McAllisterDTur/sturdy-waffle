<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    
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
		<form:options path="creditCard.brandName" items="${configuration.cardMaker}"/>
	</form:select>
	<form:errors path="creditCard.maker" cssClass="error"/>
	<br />