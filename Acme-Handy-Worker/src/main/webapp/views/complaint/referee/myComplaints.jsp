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
	<display:table name="complaints" id="complaint" requestURI="${requestURI}" pagesize="10">
			<display:column property="description" titleKey="complaint.description" />
			<display:column property="author" titleKey="complaint.author" />
			<display:column property="date" titleKey="complaint.date" sortable=true />
			<display:column>
				<a href="complaint/referee/writeReport.do?comId=${complaint.id}">
					<button type="button"><spring:message code="referee.wrte" /></button>
				</a>
			</display:column>
			<display:column>
				<a href="complaint/moreInfo.do?comId=${complaint.id}">
					<button type="button"><spring:message code="referee.more" /></button>
				</a>
			</display:column>
	</display:table>
</div>
</security:authorize>