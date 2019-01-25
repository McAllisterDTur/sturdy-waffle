<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<h2><spring:message code="administrator.dashboard.fixUpTaskStatsPerUser"/></h2>
<span><strong><spring:message code="administrator.dashboard.avg"/></strong>: <jstl:out value="${avgPerUser}"/></span>
<span><strong><spring:message code="administrator.dashboard.min"/></strong>: <jstl:out value="${minPerUser}"/></span>
<span><strong><spring:message code="administrator.dashboard.max"/></strong>: <jstl:out value="${maxPerUser}"/></span>
<span><strong><spring:message code="administrator.dashboard.dev"/></strong>: <jstl:out value="${devPerUser}"/></span>

<br/>

<h2><spring:message code="administrator.dashboard.applicationsPerTask"/></h2>
<span><strong><spring:message code="administrator.dashboard.avg"/></strong>: <jstl:out value="${avgAppNum}"/></span>
<span><strong><spring:message code="administrator.dashboard.min"/></strong>: <jstl:out value="${minAppNum}"/></span>
<span><strong><spring:message code="administrator.dashboard.max"/></strong>: <jstl:out value="${maxAppNum}"/></span>
<span><strong><spring:message code="administrator.dashboard.dev"/></strong>: <jstl:out value="${devAppNum}"/></span>

<br/>

<h2><spring:message code="administrator.dashboard.fixUpTaskStatsPrice"/></h2>
<span><strong><spring:message code="administrator.dashboard.avg"/></strong>: <jstl:out value="${avgTaskPrice}"/></span>
<span><strong><spring:message code="administrator.dashboard.min"/></strong>: <jstl:out value="${minTaskPrice}"/></span>
<span><strong><spring:message code="administrator.dashboard.max"/></strong>: <jstl:out value="${maxTaskPrice}"/></span>
<span><strong><spring:message code="administrator.dashboard.dev"/></strong>: <jstl:out value="${devTaskPrice}"/></span>

<br/>

<h2><spring:message code="administrator.dashboard.applicationStatsPrice"/></h2>
<span><strong><spring:message code="administrator.dashboard.avg"/></strong>: <jstl:out value="${avgAppPrice}"/></span>
<span><strong><spring:message code="administrator.dashboard.min"/></strong>: <jstl:out value="${minAppPrice}"/></span>
<span><strong><spring:message code="administrator.dashboard.max"/></strong>: <jstl:out value="${maxAppPrice}"/></span>
<span><strong><spring:message code="administrator.dashboard.dev"/></strong>: <jstl:out value="${devAppPrice}"/></span>

<h2><spring:message code="administrator.dashboard.ratios"/></h2>
<span><strong><spring:message code="administrator.dashboard.ratioPendingApplications"/></strong>: <jstl:out value="${pendingRatio}"/></span>
<span><strong><spring:message code="administrator.dashboard.ratioAcceptedApplications"/></strong>: <jstl:out value="${acceptedRatio}"/></span>
<span><strong><spring:message code="administrator.dashboard.ratioRejectedApplications"/></strong>: <jstl:out value="${rejectedRatio}"/></span>
<span><strong><spring:message code="administrator.dashboard.ratioUnacceptedApplications"/></strong>: <jstl:out value="${elapsedRatio}"/></span>

<h2><spring:message code="administrator.dashboard.customerTenPercentMoreApplications"/></h2>
<jstl:forEach var="customer" items="${customers}">
	<jstl:out value="${customer.account.username}"/>
	<br/>
</jstl:forEach>

<h2><spring:message code="administrator.dashboard.handyTenPercentMoreAccepted"/></h2>
<jstl:forEach var="handyWorker" items="${handyWorkers}">
	<jstl:out value="${handyWorker.account.username}"/>
	<br/>
</jstl:forEach>

<h2><spring:message code="administrator.dashboard.complaintsPerFixUpTaskStatistics"/></h2>
<span><strong><spring:message code="administrator.dashboard.avg"/></strong>: <jstl:out value="${avgComplaintsPerTask}"/></span>
<span><strong><spring:message code="administrator.dashboard.min"/></strong>: <jstl:out value="${minComplaintsPerTask}"/></span>
<span><strong><spring:message code="administrator.dashboard.max"/></strong>: <jstl:out value="${maxComplaintsPerTask}"/></span>
<span><strong><spring:message code="administrator.dashboard.dev"/></strong>: <jstl:out value="${devComplaintsPerTask}"/></span>
<span><strong><spring:message code="administrator.dashboard.taskComplaintRatio"/></strong>: <jstl:out value="${taskComplaintRatio}"/></span>

<br/>

<h2><spring:message code="administrator.dashboard.notesPerRefereeReportStatistics"/></h2>
<span><strong><spring:message code="administrator.dashboard.avg"/></strong>: <jstl:out value="${avgNotesPerReferee}"/></span>
<span><strong><spring:message code="administrator.dashboard.min"/></strong>: <jstl:out value="${minNotesPerReferee}"/></span>
<span><strong><spring:message code="administrator.dashboard.max"/></strong>: <jstl:out value="${maxNotesPerReferee}"/></span>
<span><strong><spring:message code="administrator.dashboard.dev"/></strong>: <jstl:out value="${devNotesPerReferee}"/></span>

<br/>


<br/>

<h2><spring:message code="administrator.dashboard.topThreeCustomersByComplaints"/></h2>
<jstl:forEach var="customer" items="${topThreeCustomerComplaint}">
	<jstl:out value="${customer.account.username}"/>
	<br/>
</jstl:forEach>

<h2><spring:message code="administrator.dashboard.topThreeHandyByComplaints"/></h2>
<jstl:forEach var="handy" items="${topThreeHandyComplaint}">
	<jstl:out value="${handy.account.username}"/>
	<br/>
</jstl:forEach>