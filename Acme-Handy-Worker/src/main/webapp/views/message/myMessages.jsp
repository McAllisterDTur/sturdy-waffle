<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<h1><spring:message code="messages.titl"/></h1>
<a href="messages/new.do"><spring:message code="messages.newm" /></a>
<div>
	<display:table name="messages" id="message" requestURI="${requestURI}" pagesize="10">
			<display:column property="subject" titleKey="messages.subject" />
			<display:column property="createdBy" titleKey="messages.author" />
			<display:column property="createdWhen" titleKey="messages.date" />
			<display:column property="tags" titleKey="messages.tags" />
			<display:column>
			<a href="messages/see.do?msgId=${message.id}">
				<spring:message code="messages.read" /> 
			</a>
		</display:column>
	</display:table>
</div>
