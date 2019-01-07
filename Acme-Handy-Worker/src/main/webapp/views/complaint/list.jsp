<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('REFEREE')">
	<jstl:if test="${mine }">
		<button onClick="window.location.href='complaint/referee/unassignedComplaints.do'">
			<spring:message code="complaint.referee.seeAll" />
		</button>
	</jstl:if>
	<jstl:if test="${!mine }">
		<button onClick="window.location.href='complaint/referee/myAssignedComplaints.do'">
			<spring:message code="complaint.referee.myComplaints" />
		</button>
	</jstl:if>
</security:authorize>
<security:authorize access="hasRole('CUSTOMER')">
	<jstl:if test="${!draft }">
		<button onClick="window.location.href='complaint/customer/draftedComplaints.do'">
			<spring:message code="complaint.customer.drafted" />
		</button>
	</jstl:if>
	<jstl:if test="${draft }">
		<button onClick="window.location.href='complaint/customer/finalComplaints.do'">
			<spring:message code="complaint.customer.published" />
		</button>
	</jstl:if>
	<button onClick="window.location.href='complaint/customer/new.do'">
		<spring:message code="complaint.customer.newComplaint" />
	</button>
</security:authorize>

<display:table name="complaints" id="complaint" requestURI="${requestURI}" pagesize="5">
		<display:column property="description" titleKey="complaint.description" />
		<display:column property="complaintTime" titleKey="complaint.complaintTime" />
		<security:authorize access="hasRole('REFEREE')">
			<display:column property="fixUpTask.customer.account.username" titleKey="complaint.referee.author" />
		</security:authorize>
		<security:authorize access="hasRole('CUSTOMER')">
			<display:column>
				<jstl:if test="${complaint.isFinal}">
					<jstl:if test="${not empty complaint.reports}">
						<button onClick="window.location.href='report/see.do?id=${complaint.id}'">
							<spring:message code="complaint.seeRep" />
						</button>
					</jstl:if>
					<jstl:if test="${empty complaint.reports }">
						<p><spring:message code="complaint.customer.noReport" /></p>
					</jstl:if>
				</jstl:if>
				<jstl:if test="${!complaint.isFinal}">
					<button onClick="window.location.href='complaint/customer/edit.do?id=${complaint.id}'">
						<spring:message code="complaint.customer.edit" />
					</button>
				</jstl:if>
			</display:column>
		</security:authorize>
		<security:authorize access="hasRole('REFEREE')">
			<display:column>
				<jstl:if test="${not empty complaint.reports}">
					<button onClick="window.location.href='report/see.do?id=${complaint.id}'">
						<spring:message code="complaint.seeRep" />
					</button>
				</jstl:if>
				<jstl:if test="${empty complaint.reports}">
					<jstl:if test="${not empty complaint.referee }">
						<button onClick="window.location.href='report/referee/new.do?id=${complaint.id}'">
							<spring:message code="complaint.referee.newReport" />
						</button>
					</jstl:if>
					<jstl:if test="${empty complaint.referee }">
						<button onClick="window.location.href='complaint/referee/assign.do?id=${complaint.id}'">
							<spring:message code="complaint.referee.assign" />
						</button>
					</jstl:if>		
				</jstl:if>
			</display:column>
		</security:authorize>
		<display:column>
			<button onClick="window.location.href='complaint/see.do?id=${complaint.id}'">
				<spring:message code="complaint.more" />
			</button>
		</display:column>
</display:table>