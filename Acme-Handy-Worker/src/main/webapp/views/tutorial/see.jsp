<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<% String s = request.getUserPrincipal() != null ? request.getUserPrincipal().getName() :""; %>
<jstl:set var="principal" value="<%= s %>"/>

<p style="font-size: 2em"><strong><spring:message	code="tutorial.title" />:</strong> <jstl:out value='${tutorial.title}'></jstl:out></p>
<p style="font-size: 1.5em"><strong><spring:message	code="tutorial.summary" />:</strong> <jstl:out value='${tutorial.summary}'></jstl:out></p>
<security:authorize access="hasRole('HANDYWORKER')">
	<jstl:if test="${tutorial.worker.account.username == principal}">
		<button onClick="window.location.href='section/handyworker/new.do?tutorialId=${tutorial.id}'"><spring:message code="tutorial.section.new"/></button>
	</jstl:if>
</security:authorize>
<display:table pagesize="5" name="tutorial.sections" id="row" requestURI="tutorial/display.do">

	<display:column property="number" titleKey="tutorial.section.number"></display:column>
	<display:column property="title" titleKey="tutorial.section.title"></display:column>
	<display:column property="text" titleKey="tutorial.section.text"></display:column>
	<security:authorize access="hasRole('HANDYWORKER')">
		<jstl:if test="${tutorial.worker.account.username == principal}">
		<display:column>
			<button onClick="window.location.href='section/handyworker/edit.do?id=${row.id}'"><spring:message code="tutorial.section.edit"/></button>
		</display:column>
		<display:column>
			<button onClick="window.location.href='section/handyworker/delete.do?id=${row.id}'"><spring:message code="tutorial.section.delete"/></button>
		</display:column>
		</jstl:if>
	</security:authorize>
	<display:column>
		<button onClick="window.location.href='section/pictures/list.do?id=${row.id}'"><spring:message code="tutorial.section.pictures"/></button>
	</display:column>

</display:table>