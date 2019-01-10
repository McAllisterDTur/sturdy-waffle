<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<display:table name="applications" id="row" class="displaytag"
	pagesize="6" requestURI="${requestURI }">
	<display:column property="id" titleKey="application.id" />
	<display:column property="fixUpTask.id" titleKey="application.task" />
	<display:column property="registerTime"
		titleKey="application.registerTime" />
		<display:column titleKey="application.offeredPrice">
			<b><jstl:out value="${row.offeredPrice}"></jstl:out></b><p>(<jstl:out value="${row.offeredPrice * (1+(vat/100))}"></jstl:out>)</p>
		</display:column>
			<jstl:if test="${currentDate.time gt row.fixUpTask.periodStart.time and row.status == 'PENDING' }">
				<span class="PASSED"><jstl:out value="${ row.status }" /></span>
			</jstl:if>
			<jstl:if test="${row.status != 'PENDING'}">
				<span class="${ row.status }"><jstl:out value="${ row.status }" /></span>
			</jstl:if>
		</display:column>
		<a
			href="application/customer,handyworker/display.do?applicationId=${row.id}"><spring:message
				code="application.see" /></a>
	</display:column>
	<security:authorize access="hasRole('CUSTOMER')">
		<display:column>
			<jstl:if test="${row.status == 'PENDING'}">
				<form:form
					action="application/customer/accept.do?applicationId=${row.id}"
					modelAttribute="${row}">

					<input type="hidden" />

					<input type="submit"
						value="<spring:message code="application.task.accept" />" />
				</form:form>
				</jstl:if>
		</display:column>
		<display:column>
			<jstl:if test="${row.status == 'PENDING'}">
				<form:form
					action="application/customer/reject.do?applicationId=${row.id}"
					modelAttribute="${row}">

					<input type="hidden" />

					<input type="submit"
						value="<spring:message code="application.task.reject" />" />
				</form:form>
				</jstl:if>
		</display:column>
	</security:authorize>
</display:table>
