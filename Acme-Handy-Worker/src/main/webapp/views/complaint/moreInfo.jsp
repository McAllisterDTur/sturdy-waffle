<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<div>
	<h1><spring:message code="complaint.sein"/> <jstl:out value="${complaint.ticker}"/></h1>	
	<p>
		<b><spring:message code="complaint.auth"/></b>
		<a href="customer/profile.do?cusId=<jstl:out value="${complaint.authId}"/>">
			<jstl:out value="${complaint.author}"/>
		</a>
	</p>
	<p>
		<b><spring:message code="complaint.hand"/></b>
		<a href="customer/profile.do?cusId=<jstl:out value="${complaint.handyId}"/>">
			<jstl:out value="${complaint.handyWorker}"/>
		</a>
	</p>
	<p>
		<b><spring:message code="complaint.desc"/></b>
		<jstl:out value="${complaint.description}"/>
	</p>
	<p>
		<b><spring:message code="complaint.crtd"/></b>
		<jstl:out value="${complaint.dateCreated}"/>
	</p>
	<display:table name="attachments" id="attachment" requestURI="${requestURI}" pagesize="10">
		<display:column property="attachment" titleKey="attachment" />
	</display:table>
</div>