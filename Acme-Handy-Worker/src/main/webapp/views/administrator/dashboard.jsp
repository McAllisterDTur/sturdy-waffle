<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<div style="float: left">
<h2><spring:message code="administrator.dashboard.fixUpTaskStatsPerUser"/></h2>
<span><spring:message code="administrator.dashboard.avg"/>: <jstl:out value="${avgPerUser}"/></span>
<span><spring:message code="administrator.dashboard.min"/>: <jstl:out value="${minPerUser}"/></span>
<span><spring:message code="administrator.dashboard.max"/>: <jstl:out value="${maxPerUser}"/></span>
<span><spring:message code="administrator.dashboard.dev"/>: <jstl:out value="${devPerUser}"/></span>

<br/>

<h2><spring:message code="administrator.dashboard.applicationsPerTask"/></h2>
<span><spring:message code="administrator.dashboard.avg"/>: <jstl:out value="${avgAppNum}"/></span>
<span><spring:message code="administrator.dashboard.min"/>: <jstl:out value="${minAppNum}"/></span>
<span><spring:message code="administrator.dashboard.max"/>: <jstl:out value="${maxAppNum}"/></span>
<span><spring:message code="administrator.dashboard.dev"/>: <jstl:out value="${devAppNum}"/></span>

<br/>

<h2><spring:message code="administrator.dashboard.fixUpTaskStatsPrice"/></h2>
<span><spring:message code="administrator.dashboard.avg"/>: <jstl:out value="${avgTaskPrice}"/></span>
<span><spring:message code="administrator.dashboard.min"/>: <jstl:out value="${minTaskPrice}"/></span>
<span><spring:message code="administrator.dashboard.max"/>: <jstl:out value="${maxTaskPrice}"/></span>
<span><spring:message code="administrator.dashboard.dev"/>: <jstl:out value="${devTaskPrice}"/></span>

<br/>

<h2><spring:message code="administrator.dashboard.applicationStatsPrice"/></h2>
<span><spring:message code="administrator.dashboard.avg"/>: <jstl:out value="${avgAppPrice}"/></span>
<span><spring:message code="administrator.dashboard.min"/>: <jstl:out value="${minAppPrice}"/></span>
<span><spring:message code="administrator.dashboard.max"/>: <jstl:out value="${maxAppPrice}"/></span>
<span><spring:message code="administrator.dashboard.dev"/>: <jstl:out value="${devAppPrice}"/></span>

<h2><spring:message code="administrator.dashboard.ratios"/></h2>
<span><spring:message code="administrator.dashboard.ratioPendingApplications"/>: <jstl:out value="${pendingRatio}"/></span>
<span><spring:message code="administrator.dashboard.ratioAcceptedApplications"/>: <jstl:out value="${acceptedRatio}"/></span>
<span><spring:message code="administrator.dashboard.ratioRejectedApplications"/>: <jstl:out value="${rejectedRatio}"/></span>
<span><spring:message code="administrator.dashboard.ratioUnacceptedApplications"/>: <jstl:out value="${elapsedRatio}"/></span>

</div>

<div>
<h2><spring:message code="administrator.dashboard.customerTenPercentMoreApplications"/></h2>
<jstl:forEach var="customer" items="${customers}">
	<jstl:out value="${customer.account.name}"/>
	<br/>
</jstl:forEach>

<h2><spring:message code="administrator.dashboard.handyTenPercentMoreAccepted"/></h2>
<jstl:forEach var="handyWorker" items="${handyWorkers}">
	<jstl:out value="${handyWorker.account.name}"/>
	<br/>
</jstl:forEach>
</div>