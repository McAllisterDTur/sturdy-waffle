<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="allApplications" id="application" class="displaytag" pagesize="6" requestURI="${requestURI }">
	<display:column property="fixuptask.id" titleKey="<spring:message code="handy.application.task" />" />
	<display:column property="registerTime" titleKey="<spring:message code="handy.application.registerTime" />" />
	<display:column property="offeredPrice" titleKey="<spring:message code="handy.application.offeredPrice" />" />
	<display:column property="status" titleKey="<spring:message code="handy.application.status" />" />
	<display:column>
		<a href="applications/handy-worker/show.do?id=" ><spring:message code="handy.application.show"/></a>
	</display:column>
	<display:column>
		<jstl:if test="${ row.application.status == 'ACCEPTED'}">
			<a href="applications/handy-worker/edit.do?id=" ><spring:message code="handy.application.edit"/></a>
		</jstl:if>
		<jstl:otherwise>
			<a href=""><spring:message code="handy.application.edit"/></a>
		</jstl:otherwise>
	</display:column>
</display:table>