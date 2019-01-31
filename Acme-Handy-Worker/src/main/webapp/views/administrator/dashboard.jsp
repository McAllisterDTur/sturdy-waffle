<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<h2><spring:message code="administrator.dashboard.fixUpTaskStatsPerUser"/></h2>
<span><strong><spring:message code="administrator.dashboard.avg"/></strong>: <fmt:formatNumber type="number" maxFractionDigits="2" value="${avgPerUser}"/></span>
<span><strong><spring:message code="administrator.dashboard.min"/></strong>: <fmt:formatNumber type="number" maxFractionDigits="2" value="${minPerUser}"/></span>
<span><strong><spring:message code="administrator.dashboard.max"/></strong>: <fmt:formatNumber type="number" maxFractionDigits="2" value="${maxPerUser}"/></span>
<span><strong><spring:message code="administrator.dashboard.dev"/></strong>: <fmt:formatNumber type="number" maxFractionDigits="2" value="${devPerUser}"/></span>

<br/>

<h2><spring:message code="administrator.dashboard.applicationsPerTask"/></h2>
<span><strong><spring:message code="administrator.dashboard.avg"/></strong>: <fmt:formatNumber type="number" maxFractionDigits="2" value="${avgAppNum}"/></span>
<span><strong><spring:message code="administrator.dashboard.min"/></strong>: <fmt:formatNumber type="number" maxFractionDigits="2" value="${minAppNum}"/></span>
<span><strong><spring:message code="administrator.dashboard.max"/></strong>: <fmt:formatNumber type="number" maxFractionDigits="2" value="${maxAppNum}"/></span>
<span><strong><spring:message code="administrator.dashboard.dev"/></strong>: <fmt:formatNumber type="number" maxFractionDigits="2" value="${devAppNum}"/></span>

<br/>

<h2><spring:message code="administrator.dashboard.fixUpTaskStatsPrice"/></h2>
<span><strong><spring:message code="administrator.dashboard.avg"/></strong>: <fmt:formatNumber type="number" maxFractionDigits="2" value="${avgTaskPrice}"/></span>
<span><strong><spring:message code="administrator.dashboard.min"/></strong>: <fmt:formatNumber type="number" maxFractionDigits="2" value="${minTaskPrice}"/></span>
<span><strong><spring:message code="administrator.dashboard.max"/></strong>: <fmt:formatNumber type="number" maxFractionDigits="2" value="${maxTaskPrice}"/></span>
<span><strong><spring:message code="administrator.dashboard.dev"/></strong>: <fmt:formatNumber type="number" maxFractionDigits="2" value="${devTaskPrice}"/></span>

<br/>

<h2><spring:message code="administrator.dashboard.applicationStatsPrice"/></h2>
<span><strong><spring:message code="administrator.dashboard.avg"/></strong>: <fmt:formatNumber type="number" maxFractionDigits="2" value="${avgAppPrice}"/></span>
<span><strong><spring:message code="administrator.dashboard.min"/></strong>: <fmt:formatNumber type="number" maxFractionDigits="2" value="${minAppPrice}"/></span>
<span><strong><spring:message code="administrator.dashboard.max"/></strong>: <fmt:formatNumber type="number" maxFractionDigits="2" value="${maxAppPrice}"/></span>
<span><strong><spring:message code="administrator.dashboard.dev"/></strong>: <fmt:formatNumber type="number" maxFractionDigits="2" value="${devAppPrice}"/></span>

<h2><spring:message code="administrator.dashboard.ratios"/></h2>
<span><strong><spring:message code="administrator.dashboard.ratioPendingApplications"/></strong>: <fmt:formatNumber type="number" maxFractionDigits="2" value="${pendingRatio}"/></span>
<span><strong><spring:message code="administrator.dashboard.ratioAcceptedApplications"/></strong>: <fmt:formatNumber type="number" maxFractionDigits="2" value="${acceptedRatio}"/></span>
<span><strong><spring:message code="administrator.dashboard.ratioRejectedApplications"/></strong>: <fmt:formatNumber type="number" maxFractionDigits="2" value="${rejectedRatio}"/></span>
<span><strong><spring:message code="administrator.dashboard.ratioUnacceptedApplications"/></strong>: <fmt:formatNumber type="number" maxFractionDigits="2" value="${elapsedRatio}"/></span>

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
<span><strong><spring:message code="administrator.dashboard.avg"/></strong>: <fmt:formatNumber type="number" maxFractionDigits="2" value="${avgComplaintsPerTask}"/></span>
<span><strong><spring:message code="administrator.dashboard.min"/></strong>: <fmt:formatNumber type="number" maxFractionDigits="2" value="${minComplaintsPerTask}"/></span>
<span><strong><spring:message code="administrator.dashboard.max"/></strong>: <fmt:formatNumber type="number" maxFractionDigits="2" value="${maxComplaintsPerTask}"/></span>
<span><strong><spring:message code="administrator.dashboard.dev"/></strong>: <fmt:formatNumber type="number" maxFractionDigits="2" value="${devComplaintsPerTask}"/></span>
<span><strong><spring:message code="administrator.dashboard.taskComplaintRatio"/></strong>: <fmt:formatNumber type="number" maxFractionDigits="2" value="${taskComplaintRatio}"/></span>

<br/>

<h2><spring:message code="administrator.dashboard.notesPerRefereeReportStatistics"/></h2>
<span><strong><spring:message code="administrator.dashboard.avg"/></strong>: <fmt:formatNumber type="number" maxFractionDigits="2" value="${avgNotesPerReferee}"/></span>
<span><strong><spring:message code="administrator.dashboard.min"/></strong>: <fmt:formatNumber type="number" maxFractionDigits="2" value="${minNotesPerReferee}"/></span>
<span><strong><spring:message code="administrator.dashboard.max"/></strong>: <fmt:formatNumber type="number" maxFractionDigits="2" value="${maxNotesPerReferee}"/></span>
<span><strong><spring:message code="administrator.dashboard.dev"/></strong>: <fmt:formatNumber type="number" maxFractionDigits="2" value="${devNotesPerReferee}"/></span>

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