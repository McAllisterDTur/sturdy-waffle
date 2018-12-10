<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole(HANDYWORKER)">
<div>
	<a href="messages/all.do"><spring:message code="handy.mssg" /></a>
	<display:table name="reports" id="report" requestURI="${requestURI}" pagesize="10">
		<display:column property="description" titleKey="complaint.description" />
		<display:column property="author" titleKey="complaint.author" />
		<display:column property="date" titleKey="complaint.date" sortable=true />
		<display:column>
			<jstl:if test="${not empty complaint.reportid}">
				<a href="report/see.do?id=<jstl:out value="${complaint.reportid}"/>">
					<button type="button"><spring:message code="handy.rprt" /></button>
				</a>
			</jstl:if>
			<jstl:if test="${empty complaint.reportid}">
   				<p>
					<button type="button"><spring:message code="handy.nrpt" /></button>
				</p>
			</jstl:if>
		</display:column>
	</display:table>

</div>
</security:authorize>