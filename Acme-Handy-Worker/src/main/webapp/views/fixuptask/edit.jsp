<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!-- DateTimePicker -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.22.0/moment-with-locales.min.js"></script>
<link href="./styles/datetimepicker.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="./scripts/datetimepicker.js"></script>


<%
	String s = request.getUserPrincipal() != null ? request
			.getUserPrincipal().getName() : "";
%>
<jstl:set var="principal" value="<%=s%>" />

<form:form modelAttribute="fixUpTask"
	action="fixuptask/customer/edit.do" method="post">
	<script type="text/javascript">
		$(document).ready(function() {
			$('#pickerStart').dateTimePicker({
				dateFormat : "DD/MM/YYYY HH:mm",
				selectData : "<fmt:formatDate pattern='dd/MM/yyyy HH:mm' value = '${fixUpTask.periodStart}' />" || "now"
			});
			$('#pickerEnd').dateTimePicker({
				dateFormat : "DD/MM/YYYY HH:mm",
				selectData : "<fmt:formatDate pattern='dd/MM/yyyy HH:mm' value = '${fixUpTask.periodEnd}' />" || "now"
			});
		});
	</script>

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="ticker" />
	<form:hidden path="publishTime" />
	<form:hidden path="customer" />
	<form:hidden path="creditCard" />

	<jstl:forEach var="application" items="${fixuptask.applications}">
		<form:hidden path="applications" value="${application.id}" />
	</jstl:forEach>

	<jstl:forEach var="complaint" items="${fixuptask.complaints}">
		<form:hidden path="complaints" value="${complaint.id}" />
	</jstl:forEach>


	<h3>
		<spring:message code="fixuptask.header" />
	</h3>



	<spring:message code="fixuptask.placeholder.description"
		var="placeholderdescription" />
	<form:label path="description">
		<spring:message code="fixuptask.description" />:</form:label>
	<form:input path="description" placeholder="${placeholderdescription}" />
	<form:errors cssClass="error" path="description" />
	<br />

	<spring:message code="fixuptask.placeholder.address"
		var="placeholderaddress" />
	<form:label path="address">
		<spring:message code="fixuptask.address" />:</form:label>
	<form:input path="address" placeholder="${placeholderaddress}" />
	<form:errors path="address" cssClass="error" />
	<br />

	<spring:message code="fixuptask.placeholder.maxprice"
		var="placeholdermaxprice" />
	<form:label path="maxPrice">
		<spring:message code="fixuptask.maxPrice" />:</form:label>
	<form:input type="number" step="0.01" path="maxPrice"
		placeholder="${placeholdermaxprice}" />
	<form:errors path="maxPrice" cssClass="error" />
	<br />

	<spring:message code="fixuptask.placeholder.periodstart"
		var="placeholderperiodstart" />
	<form:label path="periodStart">
		<spring:message code="fixuptask.periodStart" />:</form:label>
	<div style="width: 200px;">
		<div id="pickerStart"></div>
		<input type="hidden" id="periodStart" name="periodStart"
			value="<fmt:formatDate pattern="dd/MM/yyyy HH:mm" value="${fixUpTask.periodStart}" />" />
	</div>
	<form:errors path="periodStart" cssClass="error" />
	<jstl:if test="${dateError == 'fixuptask.date.error' }">
		<span class="error"><spring:message code='${dateError }' /></span>
	</jstl:if>

	<br />

	<spring:message code="fixuptask.placeholder.periodend"
		var="placeholderperiodend" />
	<form:label path="periodEnd">
		<spring:message code="fixuptask.periodEnd" />:</form:label>
	<div style="width: 200px;">
		<div id="pickerEnd"></div>
		<input type="hidden" id="periodEnd" name="periodEnd"
			value="<fmt:formatDate pattern="dd/MM/yyyy HH:mm" value="${fixUpTask.periodEnd}" />" />
	</div>
	<form:errors path="periodEnd" cssClass="error" />
	<br />

	<form:label path="category">
		<spring:message code="fixuptask.category" />:</form:label>
	<form:select path="category">
		<jstl:if test="${pageContext.response.locale.language == 'en'}">
			<form:options items="${categories}" itemLabel="nameEn" itemValue="id" />
		</jstl:if>
		<jstl:if test="${pageContext.response.locale.language == 'es'}">
			<form:options items="${categories}" itemLabel="name" itemValue="id" />
		</jstl:if>
	</form:select>
	<br />
	<form:label path="warranty">
		<spring:message code="fixuptask.warranty" />:</form:label>
	<form:select path="warranty">
		<jstl:forEach var="warranty" items="${warranties}">
			<form:option value="${warranty.id}"
				label="${warranty.title}: ${warranty.terms} ${warranty.law}" />
		</jstl:forEach>
	</form:select>
	<br />
	<input type="submit" value="<spring:message code='fixuptask.save'/>" />
</form:form>
