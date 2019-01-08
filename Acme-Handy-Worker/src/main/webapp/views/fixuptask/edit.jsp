<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<% String s = request.getUserPrincipal() != null ? request.getUserPrincipal().getName() :""; %>
<jstl:set var="principal" value="<%= s %>"/>

<form:form action="fixuptask/customer/edit.do" modelAttribute="fixuptask">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="ticker"/>
	<form:hidden path="publishTime"/>
	<form:hidden path="customer" />

	<jstl:forEach var="application" items="${fixuptask.applications}">
		<form:hidden path = "applications" value = "${application.id}" />
	</jstl:forEach>

	<jstl:forEach var="complaint" items="${fixuptask.complaints}">
		<form:hidden path = "complaints" value = "${complaint.id}" />
	</jstl:forEach>


	<h3><spring:message code="fixuptask.header"/> </h3>

	<spring:message code="fixuptask.placeholder.description" var="placeholderdescription"/>
	<form:label path="description"><spring:message code="fixuptask.description" />:</form:label>
	<form:input path="description" placeholder="${placeholderdescription}" />
	<form:errors path="description" cssClass="error"/>
	<br/>

	<spring:message code="fixuptask.placeholder.address" var="placeholderaddress"/>
	<form:label path="address"><spring:message code="fixuptask.address" />:</form:label>
	<form:input path="address" placeholder="${placeholderaddress}" />
	<form:errors path="address" cssClass="error"/>
	<br/>

	<spring:message code="fixuptask.placeholder.maxprice" var="placeholdermaxprice"/>
	<form:label path="maxPrice"><spring:message code="fixuptask.maxPrice" />:</form:label>
	<form:input type="number" step="0.01" path="maxPrice"  placeholder="${placeholdermaxprice}" />
	<form:errors path="maxPrice" cssClass="error"/>
	<br/>

	<spring:message code="fixuptask.placeholder.periodstart" var="placeholderperiodstart"/>
	<form:label path="periodStart"><spring:message code="fixuptask.periodStart" />:</form:label>
	<form:input type="date" path="periodStart" placeholder="${placeholderperiodstart}" />
	<form:errors path="periodStart" cssClass="error"/>
	<br/>

	<spring:message code="fixuptask.placeholder.periodend" var="placeholderperiodend"/>
	<form:label path="periodEnd"><spring:message code="fixuptask.periodEnd" />:</form:label>
	<form:input type="date" path="periodEnd" placeholder="${placeholderperiodend}" />
	<form:errors path="periodEnd" cssClass="error"/>
	<br/>

	<form:label path="category"><spring:message code="fixuptask.category" />:</form:label>
	<form:select path="category">
		<form:options items="${categories}" itemLabel="name" itemValue="id"/>
	</form:select>
	<br/>
	<form:label path="warranty"><spring:message code="fixuptask.warranty" />:</form:label>
	<form:select path="warranty">
		<jstl:forEach var="warranty" items="${warranties}">
			<form:option value="${warranty.id}" label="${warranty.title}: ${warranty.terms} ${warranty.law}"/>
		</jstl:forEach>
	</form:select>
	<br />

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

	<input type="submit" name="save" value="<spring:message code="fixuptask.save"/>" />
	<jstl:if test="${fixuptask.customer.account.username == principal}">
		<button onClick="window.location.href='/Acme-Handy-Worker/fixuptask/delete.do?id=${fixuptask.id}'">
			<spring:message code="fixuptask.delete" />
		</button>
	</jstl:if>
</form:form>
