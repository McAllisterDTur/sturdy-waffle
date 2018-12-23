<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('ADMIN')">

	<button onClick="window.location.href='category/administrator/new.do'">
		<spring:message code="category.new"/>
	</button>
	
	<jstl:if test="${not empty success }">
		<fieldset>
			<legend><spring:message code="category.message"/></legend>
			<jstl:if test="${success }">
				<spring:message code="category.success"/>
			</jstl:if>
			<jstl:if test="${not success }">
				<spring:message code="category.error"/>
			</jstl:if>
		</fieldset>
	</jstl:if>

	<display:table pagesize="5" name="categories" id="category" requestURI="category/administrator/list.do">
		<display:column property="name" titleKey="category.name"/>
		<display:column property="father.name" titleKey="category.father"/>
		<display:column>
			<button onClick="window.location.href='category/administrator/see.do?id=${category.id }'">
				<spring:message code="category.seeMore"/>
			</button>
		</display:column>
		<display:column>
			<button onClick="window.location.href='category/administrator/edit.do?id=${category.id }'">
				<spring:message code="category.edit"/>
			</button>
		</display:column>
		<display:column>
			<button onClick="window.location.href='category/administrator/delete.do?id=${category.id }'">
				<spring:message code="category.delete"/>
			</button>
		</display:column>
	</display:table>
</security:authorize>