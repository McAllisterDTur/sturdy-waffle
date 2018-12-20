<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<% String s = request.getUserPrincipal() != null ? request.getUserPrincipal().getName() :"";%>
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
	
	<form:label path="description"><spring:message code="fixuptask.description" />:</form:label>
	<form:input path="description" />
	<form:errors path="description" />
	<br/>
	<form:label path="address"><spring:message code="fixuptask.address" />:</form:label>
	<form:input path="address" />
	<form:errors path="address" />
	<br/>
	<form:label path="maxPrice"><spring:message code="fixuptask.maxPrice" />:</form:label>
	<form:input type="number" step="0.01" path="maxPrice" />
	<form:errors path="maxPrice" />
	<br/>
	<form:label path="periodStart"><spring:message code="fixuptask.periodStart" />:</form:label>
	<form:input type="date" path="periodStart" />
	<form:errors path="periodStart" />
	<br/>
	<form:label path="periodEnd"><spring:message code="fixuptask.periodEnd" />:</form:label>
	<form:input type="date" path="periodEnd" />
	<form:errors path="periodEnd" />
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
	
	<form:label path="creditCard.holderName">
		<spring:message code="creditCard.holderName" />
	</form:label>
	<form:input path="creditCard.holderName"/>
	<form:errors path="creditCard.holderName"/>
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
	<form:errors path="creditCard.codeCVV"/>
	<br />
	
	<form:label path="creditCard.maker"><spring:message code="configuration.cardMaker" />:</form:label>
	<form:select path="creditCard.maker">
		<form:options path="creditCard.maker" items="${configuration.cardMaker}"/>
	</form:select>
	<form:errors path="creditCard.maker"/>
	<br />
	
	<input type="submit" name="save" value="<spring:message code="fixuptask.save"/>" />
	<jstl:if test="${fixuptask.customer.account.username == principal}">
		<button onClick="window.location.href='/Acme-Handy-Worker/fixuptask/delete.do?id=${fixuptask.id}'"> 
			<spring:message code="fixuptask.delete" />
		</button>
	</jstl:if>
</form:form>
