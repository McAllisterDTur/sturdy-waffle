<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole(CUSTOMER)">

<jstl:if test="${empty complaint.description}">
	<h1><spring:message code="customer.edco"/></h1>
	<div>
		<form:form modelAttribute="complaint" action="complaint/customer/send.do">
			<p><spring:message code="customer.sfut"/></p>
			<form:select id="fixUpTasks" path="fixUpTask">
				<form:options items="${fixUpTasks}" itemLable="name" itemValue="id"/>
				<form:option value="0" label="-----------"/>
			</form:select>
			<p><spring:message code="customer.sdes"/></p>
			<form:textarea path="description" placeholder="<spring:message code="customer.wdes"/>"/>
			<p><spring:message code="customer.satt"/></p>
			<form:input path="attachment" placeholder="URL"/>
			<input type="submit" name="submit" value="<spring:message code="customer.send"/>"/>
			<a href="complaint/customer/save.do">
				<input type="button" name="save" value="<spring:message code="customer.save"/>"	/>
			</a>
			
		</form:form>
	</div>
</jstl:if>

<jstl:if test="${not empty complaint.description}">
	<h1><spring:message code="customer.edco"/></h1>
	<div>
		<form:form modelAttribute="complaint" action="complaint/customer/send.do">
			<p><spring:message code="customer.sfut"/></p>
			<form:select id="fixUpTasks" path="fixUpTask">
				<form:options items="${fixUpTasks}" itemLable="name" itemValue="id"/>
				<form:option value="${draft.fixUpTaskId}" label="${draft.fixUpTaskName}"/>
			</form:select>
			<p><spring:message code="customer.sdes"/></p>
			<form:textarea path="description" placeholder="<spring:message code="customer.wdes"/>"/>
			<p><spring:message code="customer.satt"/></p>
			<form:input path="attachment" placeholder="URL"/>
			<input type="submit" name="submit" value="<spring:message code="customer.send"/>"/>
			<a href="complaint/customer/save.do">
				<input type="button" name="save" value="<spring:message code="customer.save"/>"	/>
			</a>
		</form:form>
	</div>
</jstl:if>

</security:authorize>