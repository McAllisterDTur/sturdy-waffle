<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<div>
	<strong><spring:message code="application.application" />: </strong> <jstl:out value="${application.fixUpTask.id }" />
	<br />
	<strong><spring:message code="application.offeredPrice" />: </strong> <jstl:out value="${ application.offeredPrice } "></jstl:out>
	<br />
	<strong><spring:message code="application.status" />: </strong> <jstl:out value="${ application.status } "></jstl:out>
	<br />
	<jstl:if test="${application.status == 'ACCEPTED'}">
		<display:table name="application.phases" id="row" class="dispalytag" pagesize="5" requestURI="${ requestURI}" >
			<display:column property="title" titleKey="application.phase.title" />
			<display:column property="startTime" titleKey="application.phase.startTime" />
			<display:column property="endTime" titleKey="application.phase.endTime"  />
			<display:column>
				<a href="phase/handyworker/display.do?phaseId=${row.id }" ><spring:message code="application.see" /></a>
			</display:column>
		</display:table>
		<a href="phase/handyworker/create.do?applicationId=${application.id }" ><spring:message code="application.phase.create"/></a>
	</jstl:if>
</div>
