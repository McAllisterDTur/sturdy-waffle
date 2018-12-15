<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<form:form action="fixuptask/customer/edit.do"
	modelAttribute="fixuptask">
	
<% String s = request.getUserPrincipal() != null ? request.getUserPrincipal().getName() :""; %>
<jstl:set var="principal" value="<%= s %>"/>

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="ticker" />
	<form:hidden path="customer" />
	<form:hidden path="publishTime" />
	<form:hidden path="description" />
	<form:hidden path="address" />
	<form:hidden path="maxPrice" />
	<form:hidden path="periodStart" />
	<form:hidden path="periodEnd" />
	<form:hidden path="category" />
	<form:hidden path="warranty" />
	<form:hidden path="creditCard" />

	<b><form:label path="ticker">
			<spring:message code="fixuptask.ticker" />:&nbsp;</form:label></b>
	<input value="${fixuptask.ticker}" readonly="readonly" />
	<form:errors path="ticker" cssClass="error" />
	<br />

	<b><form:label path="customer">
			<spring:message code="fixuptask.customer" />:&nbsp;</form:label></b>
	<input value="${fixuptask.customer}" readonly="readonly" />
	<form:errors path="ticker" cssClass="error" />
	<br />

	<b><form:label path="publishTime">
			<spring:message code="fixuptask.publishTime" />:&nbsp;</form:label></b>
	<form:textarea path="publishTime" />
	<form:errors path="publishTime" cssClass="error" />
	<br />

	<b><form:label path="description">
			<spring:message code="fixuptask.description" />:&nbsp;</form:label></b>
	<form:textarea path="description" />
	<form:errors path="description" cssClass="error" />
	<br />

	<b><form:label path="address">
			<spring:message code="fixuptask.address" />:&nbsp;</form:label></b>
	<form:textarea path="address" />
	<form:errors path="address" cssClass="error" />
	<br />

	<b><form:label path="maxPrice">
			<spring:message code="fixuptask.maxPrice" />:&nbsp;</form:label></b>
	<form:input path="maxPrice" placeholder="1000.0" />
	<form:errors path="maxPrice" cssClass="error" />
	<br />

	<b><form:label path="periodStart">
			<spring:message code="fixuptask.periodStart" />:&nbsp;</form:label></b>
	<form:input path="periodStart" placeholder="dd/MM/yyyy HH:mm" />
	<form:errors path="periodStart" cssClass="error" />
	<br />

	<b><form:label path="periodEnd">
			<spring:message code="fixuptask.periodEnd" />:&nbsp;</form:label></b>
	<form:input path="periodEnd" placeholder="dd/MM/yyyy HH:mm" />
	<form:errors path="periodEnd" cssClass="error" />
	<br />

	<b><form:label path="category">
			<spring:message code="fixuptask.category" />:&nbsp;</form:label></b>
	<form:select path="category">
		<form:option label="----" value="0" />
		<form:options items="${categories}" itemLabel="name" itemValue="id" />
	</form:select>
	<form:errors path="category" cssClass="error" />
	<br />

	<b><form:label path="warranty">
			<spring:message code="fixuptask.warranty" />:&nbsp;</form:label></b>
	<form:select path="warranty">
		<form:option label="----" value="0" />
		<form:options items="${warranty}" itemLabel="title" itemValue="id" />
	</form:select>
	<form:errors path="warranty" cssClass="error" />
	<br />

	<form:label path="creditCard.holderName">
		<spring:message code="creditCard.holderName" />
	</form:label>
	<form:input path="creditCard.holderName" />
	<form:errors path="creditCard.holderName" cssClass="error" />
	<br />

	<form:label path="creditCard.brandName">
		<spring:message code="creditCard.brandName" />
	</form:label>
	<form:input path="creditCard.brandName" />
	<form:errors path="creditCard.brandName" cssClass="error" />
	<br />

	<form:label path="creditCard.number">
		<spring:message code="creditCard.number" />
	</form:label>
	<form:input path="creditCard.number" />
	<form:errors path="creditCard.number" cssClass="error" />
	<br />

	<form:label path="creditCard.expirationMonth">
		<spring:message code="creditCard.expirationMonth" />
	</form:label>
	<form:input path="creditCard.expirationMonth" />
	<form:errors path="creditCard.expirationMonth" cssClass="error" />
	<br />

	<form:label path="creditCard.expirationYear">
		<spring:message code="creditCard.expirationYear" />
	</form:label>
	<form:input path="creditCard.expirationYear" />
	<form:errors path="creditCard.expirationYear" cssClass="error" />
	<br />

	<form:label path="creditCard.codeCVV">
		<spring:message code="creditCard.codeCVV" />
	</form:label>
	<form:input path="creditCard.codeCVV" />
	<form:errors path="creditCard.codeCVV" cssClass="error" />
	<br />

	<b><form:label path="${configuration.cardMaker}">
			<spring:message code="configuration.cardMaker" />:&nbsp;</form:label></b>
	<form:select path="${configuration.cardMaker}">
		<form:option label="----" value="0" />
		<form:options items="${configuration.cardMaker}" itemLabel="title" itemValue="id" />
	</form:select>
	<form:errors path="warranty" cssClass="error" />
	<br />

	<input type="submit" name="save"
		value="<spring:message code="fixuptask.save"/>" />
	&nbsp;
	<jstl:if test="${fixuptask.customer.account == principal}">
		<button
			onClick="window.location.href='/Acme-Handy-Worker/fixuptask/delete.do?id=${row.id}'">
			<spring:message code="fixuptask.delete" />
		</button>
	</jstl:if>
	<input type="button" name="cancel"
		value="<spring:message code="fixuptask.cancel" />"
		onclick="javascript: relativeRedir('fixuptask/list.do');" />
	&nbsp;
</form:form>
