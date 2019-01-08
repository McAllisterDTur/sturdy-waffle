<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('ADMIN')">

	<button onClick="window.location.href='category/administrator/list.do'">
		<spring:message code="category.back"/>
	</button>
	
	<jstl:if test="${not empty success }">
		<fieldset>
			<legend><spring:message code="category.message"/></legend>
			<jstl:if test="${not success }">
				<spring:message code="category.error"/>
				<jstl:if test="${father }">
				. <spring:message code="category.errorFather"/>
				</jstl:if>
			</jstl:if>
		</fieldset>
	</jstl:if>
		
	<form:form modelAttribute="category" action="category/administrator/save.do">
		<p>
			<spring:message code="category.name"/>: 
			<form:input path="name"/>
			<form:errors path="name" cssClass="error"/>
		</p>
		<p>
			<spring:message code="category.nameEn"/>: 
			<form:input path="nameEn"/>
			<form:errors path="nameEn" cssClass="error"/>
		</p>
		<p>
			<spring:message code="category.father"/>: 
			<form:select path="father">
				<form:option value="0" label="-------"/>
				<form:options items="${categories }" itemLabel="name" itemValue="id" />				
			</form:select>
			<form:errors path="father" cssClass="error"/>
		</p>
		<form:hidden path="id" />
		<form:hidden path="version" />
		<input type="submit" name="save" value="<spring:message code="category.save"/>"/>
	</form:form>
	
</security:authorize>