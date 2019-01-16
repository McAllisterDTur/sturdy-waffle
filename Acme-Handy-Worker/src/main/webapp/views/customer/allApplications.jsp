<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="allApplications" id="application" class="displaytag" pagesize="6" requestURI="${requestURI }">
	<display:column property="fixuptask.id" titleKey="<spring:message code="customer.application.task" />" />
	<display:column property="registerTime" titleKey="<spring:message code="customer.application.registerTime" />" />
	<display:column property="offeredPrice" titleKey="<spring:message code="customer.application.offeredPrice" />" />
	<display:column property="status" titleKey="<spring:message code="customer.application.status" />" />
	<display:column>
		<a href="applications/customer/show.do?id=" ><spring:message code="customer.application.show"/></a>
	</display:column>
	<display:column>
			<a href="applications/customer/edit.do?id=" ><spring:message code="customer.application.edit"/></a>
	</display:column>
	<display:column>
			<a href="applications/customer/accept.do?id=" ><spring:message code="customer.application.accept"/></a>
	</display:column>
	<display:column>
			<a href="applications/customer/reject.do?id=" ><spring:message code="customer.application.reject"/></a>
	</display:column>
</display:table>