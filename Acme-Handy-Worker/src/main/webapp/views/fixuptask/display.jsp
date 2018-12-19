<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<% String s = request.getUserPrincipal() != null ? request.getUserPrincipal().getName() :"";%>
<jstl:set var="principal" value="<%= s %>"/>

<h3>
	<b><spring:message code="fixuptask.ticker" />:&nbsp;</b>
	<jstl:out value="${fixuptask.ticker}" />
</h3>

<b><spring:message code="fixuptask.publishTime" />:&nbsp;</b>
<jstl:out value="${fixuptask.publishTime}" />
<br />

<b><spring:message code="fixuptask.description" />:&nbsp;</b>
<jstl:out value="${fixuptask.description}" />
<br />

<b><spring:message code="fixuptask.address" />:&nbsp;</b>
<jstl:out value="${fixuptask.address}" />
<br />

<b><spring:message code="fixuptask.category" />:&nbsp;</b>
<jstl:out value="${fixuptask.category.name}" />
<br />

<b><spring:message code="fixuptask.periodStart" />:&nbsp;</b>
<jstl:out value="${fixuptask.periodStart}" />
<br />

<b><spring:message code="fixuptask.periodEnd" />:&nbsp;</b>
<jstl:out value="${fixuptask.periodEnd}" />
<br />

<b><spring:message code="fixuptask.maxPrice" />:&nbsp;</b>
<jstl:out value="${fixuptask.maxPrice}" />
<br />

<b><spring:message code="fixuptask.customer" />:&nbsp;</b>
<jstl:out value="${fixuptask.customer.name}" />
<br />

<b><spring:message code="fixuptask.warranty" />:&nbsp;</b>
<jstl:out value="${fixuptask.title}" />
<br />

<security:authorize access="hasRole('CUSTOMER')">
		<jstl:if test="${fixuptask.customer.account.username == principalId}">
			<button
				onClick="window.location.href='/Acme-Handy-Worker/fixuptask/edit.do?id=${row.id}'">
				<spring:message code="fixuptask.edit" />
			</button>
		</jstl:if>
		<jstl:if test="${fixuptask.worker.account.username == principalId}">
			<button
				onClick="window.location.href='/Acme-Handy-Worker/fixuptask/delete.do?id=${row.id}'">
				<spring:message code="fixuptask.delete" />
			</button>
		</jstl:if>
</security:authorize>
