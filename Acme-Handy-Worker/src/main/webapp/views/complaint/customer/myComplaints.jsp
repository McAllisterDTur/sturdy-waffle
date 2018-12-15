<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole(CUSTOMER)">
<div>
	<a href="complaint/customer/findAll.do?id=<jstl:out value="${customerid}" />"><spring:message code="customer.back" /></a>
	<display:table name="complaints" id="complaint" requestURI="${row.requestURI}" pagesize="10">
			<display:column property="description" titleKey="complaint.description" />
			<display:column property="handyworker" titleKey="complaint.handyWorker" />
			<display:column property="date" titleKey="complaint.date" sortable=true />
			<display:column>
				<form:form action="complaint/edit.do?id=${row.complaint.id}">
					<input type="submit" value="<spring:message code="customer.edit" />"/>
				</form:form>
			</display:column>
			<display:column>
				<form:form action="complaint/moreInfo.do?id=${row.complaint.id}">
					<input type="submit" value="<spring:message code="customer.more" />"/>
				</form:form>
			</display:column>
	</display:table>
</div>
</security:authorize>