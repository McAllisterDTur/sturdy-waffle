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
	
	<p>
		<b><spring:message code="category.name"/>: </b>
		<jstl:out value="${category.name }"/>
	</p>
	<p>
		<b><spring:message code="category.nameEn"/>: </b>
		<jstl:out value="${category.nameEn }"/>
	</p>
	
	<jstl:if test="${pageContext.response.locale.language == 'en'}">
		<p>
			<b><spring:message code="category.father"/>: </b>
			<jstl:out value="${category.father.nameEn }"/>
		</p>
	</jstl:if>
	<jstl:if test="${pageContext.response.locale.language == 'es'}">
	<p>
			<b><spring:message code="category.father"/>: </b>
			<jstl:out value="${category.father.name }"/>
		</p>
	</jstl:if>
	
	<p>
		<button onClick="window.location.href='category/administrator/edit.do?id=${category.id }'">
			<spring:message code="category.edit"/>
		</button>
		<button onClick="window.location.href='category/administrator/delete.do?id=${category.id }'">
			<spring:message code="category.delete"/>
		</button>
	</p>
	
</security:authorize>