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
	<display:column property="offeredPrice"
		titleKey="application.offeredPrice" />
	<display:column property="status" titleKey="application.status" />
	<display:column>
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
					
					<input type="hidden" value="ACCEPTED" />
					
					<input type="submit"
						value="<spring:message code="application.task.accept" />" />

				</form:form>
			</jstl:if>
		</display:column>
	</security:authorize>
</display:table>