<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole(REFEREE)">
<div>
	<a href="complaint/referee/listAll">All</a>
	<a href="complaint/referee/myComplaints.do?refId=<jstl:out value="${refereeid}" />"></a>
	<table>
			<tr>
				<th><spring:message code="referee.desc" /></th>
				<th><spring:message code="referee.autr" /></th>
				<th><spring:message code="referee.date" /></th>
			</tr>
		<jstl:forEach items="${list}" var="complaint">
			<tr>
				<td><jstl:out value="${complaint.description}" /></td>
				<td><a href="customer/profile.do?cusId=<jstl:out value="${complaint.author}"/>">
					<jstl:out value="${complaint.author}"/>
					</a>
				</td>
				<td><jstl:out value="${complaint.date}" /></td>
				<td>
					<a href="complaint/referee/writeReport.do?comId=<jstl:out value="${complaint.id}"/>">
						<button type="button"><spring:message code="referee.wrte" /></button>
					</a>
				</td>
				<td>
					<a href="complaint/moreInfo.do?comId=<jstl:out value="${complaint.id}"/>">
						<button type="button"><spring:message code="referee.more" /></button>
					</a>
				</td>
			</tr>
		</jstl:forEach>
	</table>
</div>
</security:authorize>