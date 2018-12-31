<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<jstl:if test="${not empty success && !success }">
	<p><spring:message code="endorsement.edit.error"/></p>
</jstl:if>

<form:form id="endorsementForm" modelAttribute="endorsement" action="endorsement/handyworker,customer/save.do" method="POST">
	<p>
		<spring:message code="endorsement.edit.receiver"/>: 
		<form:select path="reciever">
			<form:option value="0" label="-------"/>
			<form:options items="${users }" itemLabel="account.username" itemValue="id" />				
		</form:select>
		<form:errors path="reciever" cssClass="error"/>
	</p>
	<p>
		<spring:message code="endorsement.edit.comment"/>:
		<form:textarea path="comment"/>
		<form:errors path="comment" cssClass="errors" />
	</p>
	
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="sender"/>
	<form:hidden path="writeTime"/>
	
	<input type="submit" name="save" value="<spring:message code='endorsement.edit.save'/>"/>
</form:form>