<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('REFEREE')">
	<button onClick="window.location.href='complaint/referee/listAll.do'">
		<spring:message code="complaint.referee.seeAll" />
	</button>
	<button onClick="window.location.href='complaint/referee/myComplaints.do?refId=${refereeid}'">
		<spring:message code="complaint.referee.myComplaints" />
	</button>
</security:authorize>
<security:authorize access="hasRole('CUSTOMER')">
	<button onClick="window.location.href='complaint/customer/drafted.do?id=${customerid}'">
		<spring:message code="complaint.customer.drafted" />
	</button>
	<button onClick="window.location.href='complaint/customer/new.do'">
		<spring:message code="complaint.customer.newComplaint" />
	</button>
</security:authorize>

<display:table name="complaints" id="complaint" requestURI="${requestURI}" pagesize="10">
		<display:column property="description" titleKey="complaint.description" />
		<display:column property="date" titleKey="complaint.complaintTime" sortable=true />
		<security:authorize access="hasRole('REFEREE')">
			<display:column property="fixUpTask.customer.userAccount.username" titleKey="complaint.referee.author" />
		</security:authorize>
		<display:column>
			<jstl:if test="${not empty complaint.report.id}">
				<form:form action="report/see.do?id=${row.complaint.reportid}">
					<input type="submit" value="<spring:message code="complaint.seeRep" />"/>
				</form:form>
			</jstl:if>
			<jstl:if test="${empty complaint.report.id}">
				<security:authorize access="hasRole('REFEREE')">
					<jstl:if test="${not empty complaint.referee }">
						<form:form action="report/referee/new.do?id=${complaint.id}">
							<input type="submit" value="<spring:message code="complaint.referee.newReport" />"/>
						</form:form>
					</jstl:if>
					<jstl:if test="${empty complaint.referee }">
						<form:form action="complaint/referee/assign.do?id=${complaint.id}">
							<input type="submit" value="<spring:message code="complaint.referee.assign" />"/>
						</form:form>
					</jstl:if>
				</security:authorize>
				<security:authorize access="hasRole('CUSTOMER')">
					<jstl:if test=${final }>
						<p><spring:message code="complaint.customer.noReport" /></p>
					</jstl:if>
					<jstl:if test=${!final }>
						<form:form action="complaint/customer/edit.do?id=${complaint.id}">
							<input type="submit" value="<spring:message code="complaint.customer.edit" />"/>
						</form:form>
					</jstl:if>
				</security:authorize>		
			</jstl:if>
		</display:column>
		<display:column>
			<form:form action="complaint/seeMore.do?id=${row.complaint.id}">
				<input type="submit" value="<spring:message code="referee.more" />"/>
			</form:form>
		</display:column>
</display:table>