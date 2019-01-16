<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('ADMIN')">

	<button onClick="window.location.href='warranty/administrator/list.do'">
		<spring:message code="warranty.back"/>
	</button>
	
	<p>
		<b><spring:message code="warranty.title"/>: </b>
		<jstl:out value="${warranty.title }"/>
	</p>
	
	<p>
		<b><spring:message code="warranty.terms"/>: </b>
		<jstl:out value="${warranty.terms }"/>
	</p>

	<p><b><spring:message code="warranty.law"/>: </b></p>
	<li>
	<jstl:forEach items="${warranty.law }" var="law">
		<ul><jstl:out value="${law }"/></ul>
	</jstl:forEach>
	</li>
	
	<jstl:if test="${warranty.draft }">
		<p>
			<button onClick="window.location.href='warranty/administrator/edit.do?id=${warranty.id }'">
				<spring:message code="warranty.edit"/>
			</button>
			<button onClick="window.location.href='warranty/administrator/delete.do?id=${warranty.id }'">
				<spring:message code="warranty.delete"/>
			</button>
		</p>
	</jstl:if>
	
</security:authorize>