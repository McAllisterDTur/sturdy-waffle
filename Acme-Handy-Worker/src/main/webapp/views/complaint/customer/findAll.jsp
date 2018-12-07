<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole(CUSTOMER)">
<div>
	<a href="complaint/customer/drafted?cusId=<jstl:out value="${customerid}" />"><spring:message code="customer.drft" /></a>
	
	<table>
			<tr>
				<th><spring:message code="customer.desc" /></th>
				<th><spring:message code="customer.hand" /></th>
				<th><spring:message code="customer.date" /></th>
			</tr>
		<jstl:forEach items="${list}" var="complaint">
			<tr>
				<td><jstl:out value="${complaint.description}" /></td>
				<td><a href="handyworker/profile.do?handyId=<jstl:out value="${complaint.handyId}"/>">
					<jstl:out value="${complaint.handy}"/>
					</a>
				</td>
				<td><jstl:out value="${complaint.date}" /></td>
				<jstl:if test="${not empty complaint.reportid}">
    				<td>
					<a href="report/see.do?id=<jstl:out value="${complaint.reportid}"/>">
						<button type="button"><spring:message code="customer.rprt" /></button>
					</a>
				</td>
				</jstl:if>
				<td>
					<a href="complaint/moreInfo.do?comId=<jstl:out value="${complaint.id}"/>">
						<button type="button"><spring:message code="customer.more" /></button>
					</a>
				</td>
				<td>
					<a href="complaint/delete.do?comId=<jstl:out value="${complaint.id}"/>">
						<button type="button"><spring:message code="customer.dlte" /></button>
					</a>
				</td>
			</tr>
		</jstl:forEach>
		
	</table>
</div>
</security:authorize>