<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%
	String s = request.getUserPrincipal() != null ? request.getUserPrincipal().getName() : "";
%>
<jstl:set var="principal" value="<%=s%>" />
<div>
	<display:table name="notes" id="note" requestURI="${requestURI}"
		pagesize="10">

		<display:column property="moment" titleKey="note.moment" />
		
		<display:column titleKey="notes.seemore">
			<button
				onClick="window.location.href='/Acme-Handy-Worker/notes/display.do?noteId=${note.id}'">
				<spring:message code="notes.seemore" />
			</button>
		</display:column>

		<display:column titleKey="note.comment">
			<button
				onClick="window.location.href='/Acme-Handy-Worker/notes/edit.do?noteId=${note.id}'">
				<spring:message code="note.comment" />
			</button>
		</display:column>

	</display:table>
</div>