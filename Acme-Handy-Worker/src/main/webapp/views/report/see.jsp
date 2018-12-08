<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<div>
	<h2><spring:message code="report.rfvd" /></h2>
	<p><jstl:out value="${report.veredict }"/></p>
	<p>
		<spring:message code="report.cplr" />: 
		<a href="customer/profile.do?id=<jstl:out value="${report.complainerId}"/>">
			<jstl:out value="${report.complainer}"/>
		</a>
	</p>
	<p>
		<spring:message code="report.hand" />: 
		<a href="handyworker/profile.do?id=<jstl:out value="${report.handyId}"/>">
			<jstl:out value="${report.handy}"/>
		</a>
	</p>
	<p>
		<spring:message code="report.comp" />: 
		<a href="complaint/moreInfo?id=<jstl:out value="${report.complaintId}"/>">
			<jstl:out value="${report.complaintTicker}"/>
		</a>
	</p>
	<p>
		<a href="notes/see?id=<jstl:out value="${report.id}"/>">
			<spring:message code="report.nots" /> 	
		</a>
	</p>
</div>