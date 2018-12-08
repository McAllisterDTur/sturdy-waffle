<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole(HANDYWORKER)">
<div>
	<a href="messages/all.do"><spring:message code="handy.mssg" /></a>
	<table>
			<tr>
				<th><spring:message code="handy.desc" /></th>
				<th><spring:message code="handy.autr" /></th>
				<th><spring:message code="handy.date" /></th>
			</tr>
		<jstl:forEach items="${reportList}" var="complaint">
			<tr>
				<td><jstl:out value="${complaint.description}" /></td>
				<td><a href="customer/profile.do?cusId=<jstl:out value="${complaint.authorId}"/>">
					<jstl:out value="${complaint.author}"/>
					</a>
				</td>
				<td><jstl:out value="${complaint.date}" /></td>
				<jstl:if test="${not empty complaint.reportid}">
    				<td>
					<a href="report/see.do?id=<jstl:out value="${complaint.reportid}"/>">
						<button type="button"><spring:message code="handy.rprt" /></button>
					</a>
				</td>
				</jstl:if>
				<jstl:if test="${empty complaint.reportid}">
    				<td>
						<button type="button"><spring:message code="handy.nrpt" /></button>
					</td>
				</jstl:if>
			</tr>
		</jstl:forEach>
		
	</table>
</div>
</security:authorize>