<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<div>
	<h2>
		<spring:message code="handy.rprt" />
		: ${report.complaint.ticker }
	</h2>
	<br /> <b><spring:message code="report.referee" /></b>:
	<p>${referee}</p>
	<b><spring:message code="report.customer" /></b>:
	<p>${customer}</p>
	<b><spring:message code="report.worker" /></b>:
	<p>${worker}</p>
	<b><spring:message code="report.description" /></b>
	<p>${report.description}</p>
	<br /> <b><spring:message code="report.final" /></b>
	<p>${report.isFinal}</p>
	<br />
	<display:table name="notes" id="note" pagesize="10"
		requestURI="requestURI">
		<display:column property="moment" titleKey="report.date" />
		<display:column>
			<a href="/notes/display.do?noteId=${note.id}"><spring:message
					code="report.seeMore" /> </a>
		</display:column>
	</display:table>
	<security:authorize access="hasRole('REFEREE')">
		<jstl:if test="${report.isFinal }"> <!-- TODO: añadir not -->
			<button
				onClick="window.location.href='/Acme-Handy-Worker/report/referee/delete.do?reportId=${report.id}">
				<spring:message code="report.delete" />
			</button>
			<button
				onClick="window.location.href='/Acme-Handy-Worker/report/referee/edit.do?reportId=${report.id}">
				<spring:message code="report.edit" />
			</button>
		</jstl:if>
	</security:authorize>
</div>