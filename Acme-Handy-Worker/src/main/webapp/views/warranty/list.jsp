<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('ADMIN')">

	<button onClick="window.location.href='warranty/administrator/new.do'">
		<spring:message code="warranty.new"/>
	</button>
	
	<jstl:if test="${not empty success }">
		<fieldset>
			<legend><spring:message code="warranty.message"/></legend>
			<jstl:if test="${success }">
				<spring:message code="warranty.success"/>
			</jstl:if>
			<jstl:if test="${not success }">
				<spring:message code="warranty.error"/>
			</jstl:if>
		</fieldset>
	</jstl:if>

	<display:table pagesize="5" name="warranties" id="warranty" requestURI="warranty/administrator/list.do">
		<display:column property="title" titleKey="warranty.title"/>
		<display:column property="terms" titleKey="warranty.terms"/>
		<display:column sortable="true">
			<jstl:if test="${warranty.draft }">
				<button onClick="window.location.href='warranty/administrator/edit.do?id=${warranty.id }'">
					<spring:message code="warranty.edit"/>
				</button>
			</jstl:if>
		</display:column>
		<display:column>
			<jstl:if test="${warranty.draft }">
				<button onClick="window.location.href='warranty/administrator/delete.do?id=${warranty.id }'">
					<spring:message code="warranty.delete"/>
				</button>
			</jstl:if>
		</display:column>
		<display:column>
			<button onClick="window.location.href='warranty/administrator/see.do?id=${warranty.id }'">
				<spring:message code="warranty.seeMore"/>
			</button>
		</display:column>
	</display:table>
</security:authorize>