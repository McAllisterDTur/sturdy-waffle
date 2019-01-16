<%@page import="java.sql.Timestamp"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<div>
	<strong><spring:message code="application.application" />: </strong>
	<jstl:out value="${application.fixUpTask.id }" />
	<br /> <strong><spring:message
			code="application.offeredPrice" />: </strong>
	<jstl:out value="${ application.offeredPrice } "></jstl:out>
	<br />
	<strong><spring:message code="application.offeredPrice" />: </strong> <b><jstl:out value="${application.offeredPrice}"></jstl:out></b><p>(<jstl:out value="${application.offeredPrice * (1+(vat/100))}"></jstl:out>)</p>
	<br />
	<jstl:if
		test="${currentDate.time gt application.fixUpTask.periodStart.time and application.status == 'PENDING'}">
		<strong><spring:message code="application.status" />: </strong>
		<span class="PASSED"><jstl:out value="${ application.status } " /></span>
	</jstl:if>
	<jstl:if
		test="${application.status != 'PENDING'}">
		<strong><spring:message code="application.status" />: </strong>
		<span class="${ application.status }"><jstl:out
				value="${ application.status } " /></span>
	</jstl:if>

	<br />
	<spring:message code="application.handy.comments"/>
	<br />
	<jstl:forEach items="${application.handyComments }" var="comment">
		<jstl:out value="${ comment}" />
		<br />
	</jstl:forEach>
	<br />
	<spring:message code="application.customer.comments"/>
	<br />
	<jstl:forEach items="${application.customerComments }" var="comment">
		<jstl:out value="${ comment}" />
		<br />
	</jstl:forEach>
	<br />
	<jstl:if test="${application.status == 'ACCEPTED'}">
		<display:table name="application.phases" id="row" class="dispalytag" pagesize="5" requestURI="${ requestURI}" >
			<display:column property="title" titleKey="application.phase.title" />
			<display:column property="startTime"
				titleKey="application.phase.startTime" />
			<display:column property="endTime"
				titleKey="application.phase.endTime" />
			<display:column>
				<a href="phase/handyworker/display.do?phaseId=${row.id }"><spring:message
						code="application.see" /></a>
			</display:column>
		</display:table>
		<security:authorize access="hasRole('HANDYWORKER')">
			<a href="phase/handyworker/create.do?applicationId=${application.id }" ><spring:message code="application.phase.create"/></a>
		</security:authorize>
		<security:authorize access="hasRole('CUSTOMER')">
			<jstl:if test="${application.fixUpTask.customer == customer }">
				<a href="application/customer,handyworker/edit.do?applicationId=${application.id}"><spring:message code="application.addComment" /></a>
			</jstl:if>

		</security:authorize>

	</jstl:if>
</div>
