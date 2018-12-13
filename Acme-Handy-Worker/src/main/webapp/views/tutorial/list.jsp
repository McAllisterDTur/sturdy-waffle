<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<jstl:set var="principalId" value="<%= request.getUserPrincipal() %>"/>

<security:authorize access="hasRole('HANDYWORKER')">
	<button onClick="window.location.href='/Acme-Handy-Worker/tutorial/all.do'"><spring:message code="tutorial.all"/></button>
	<button onClick="window.location.href='/Acme-Handy-Worker/tutorial/myTutorials.do'"><spring:message code="tutorial.myTutorials"/></button>
	<button onClick="window.location.href='/Acme-Handy-Worker/tutorial/new.do'"><spring:message code="tutorial.new"/></button>
</security:authorize>
<display:table pagesize="5" name="tutorials" id="row" requestURI="tutorial/${url}.do">
	

	<display:column property="title" titleKey="tutorial.title"></display:column>
	<display:column titleKey="tutorial.handyworker"><a href="profile/handyWorker.do?id=${row.worker.id}"><jstl:out value='${row.worker.make}'></jstl:out></a></display:column>
	<display:column property="summary" titleKey="tutorial.summary"></display:column>
	<display:column property="lastTimeUpdated" titleKey="tutorial.lastupdate"></display:column>
	
	<display:column>
		<button onClick="window.location.href='/Acme-Handy-Worker/tutorial/see.do?id=${row.id}'"><spring:message code="tutorial.see"/></button>
		
	</display:column>
	
	<security:authorize access="hasRole('HANDYWORKER')">
		<display:column>
		<jstl:if test="${row.worker.account.username == principalId.name}">
			<button onClick="window.location.href='/Acme-Handy-Worker/tutorial/edit.do?id=${row.id}'"><spring:message code="tutorial.edit"/></button>
		</jstl:if>
		</display:column>
		<display:column>
		<jstl:if test="${row.worker.account.username == principalId.name}">
			<button onClick="window.location.href='/Acme-Handy-Worker/tutorial/delete.do?id=${row.id}'"><spring:message code="tutorial.delete"/></button>
		</jstl:if>
		</display:column>
	</security:authorize>
	<display:column>
		<display:column>
			<button onClick="window.location.href='/Acme-Handy-Worker/tutorial/pictures.do?id=${row.id}'"><spring:message code="tutorial.pictures"/></button>
		</display:column>
	</display:column>
</display:table>