<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<div>
	<h1><spring:message code="complaint.more.seeing"/> <jstl:out value="${complaint.ticker}"/></h1>	
	<p>
		<b><spring:message code="complaint.more.author"/></b>: 
		<jstl:out value="${author.account.username}"/>
	</p>
	<p>
		<b><spring:message code="complaint.more.handy"/></b>: 
		<jstl:out value="${handy.account.username}"/>
	</p>
	<p>
		<b><spring:message code="complaint.more.description"/></b>: 
		<jstl:out value="${complaint.description}"/>
	</p>
	<p>
		<b><spring:message code="complaint.more.timeCreated"/></b>: 
		<jstl:out value="${complaint.complaintTime}"/>
	</p>
	<p>
		<b><spring:message code="complaint.more.fixUpTask"/></b>: 
		<jstl:out value="${complaint.fixUpTask.description}"/>
	</p>
	<p>
		<b><spring:message code="complaint.more.referee"/></b>: 
		<jstl:if test="${not empty complaint.referee }">
			<jstl:out value="${complaint.referee.account.username}"/>
		</jstl:if>
		<jstl:if test="${empty complaint.referee }">
			<spring:message code="complaint.more.noReferee"/>
		</jstl:if>
	</p>
	<p><b><spring:message code="complaint.more.attachment"/></b>:</p>
	<jstl:forEach items="${complaint.attachments }" var="attachment">
		<img src="attachment"/>
	</jstl:forEach>
</div>