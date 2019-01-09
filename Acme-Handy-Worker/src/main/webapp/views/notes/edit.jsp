<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<jstl:if test="${pageContext.response.locale.language == 'en' }">
	<script type='text/javascript'>	
		function sendForm(){
			if(confirm("Do you want to send this note?\nThis action can't be undone.")){
				document.getElementById("noteSubmit").submit();
			} else{
				//window.location.href=document.referrer;
				return false;
			}
		}
	</script>
</jstl:if>
<jstl:if test="${pageContext.response.locale.language == 'es' }">
	<script type='text/javascript'>	
		function sendForm(){
			if(confirm("¿Quieres enviar esta nota?\nEsta acción no se puede deshacer.")){
				document.getElementById("noteSubmit").submit();
			}else{
				window.location.href=document.referrer;
			}
		}
	</script>
</jstl:if>

<%
	String s = request.getUserPrincipal() != null ? request.getUserPrincipal().getName() : "";
%>
<jstl:set var="principal" value="<%=s%>" />

<form:form id="noteSubmit" action="notes/edit.do" modelAttribute="note" method="POST">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="report" />
	<form:hidden path="moment" />

	<h3>
		<spring:message code="notes.header" />
	</h3>

	<security:authorize access="hasRole('CUSTOMER')">
		<form:hidden path="handyComments" />
		<form:label path="customerComments">
			<spring:message code="notes.write.comment" />:</form:label>
		<form:textarea path="customerComments" />
		<form:errors path="customerComments" />
		<br />
	</security:authorize>

	<security:authorize access="hasRole('REFEREE')">
		<form:hidden path="customerComments" />
		<form:hidden path="handyComments" />
		<form:label path="refereeComments">
			<spring:message code="notes.write.comment" />:</form:label>
		<form:textarea path="refereeComments" />
		<form:errors path="refereeComments" />
		<br />

	</security:authorize>

	<security:authorize access="hasRole('HANDYWORKER')">
		<form:hidden path="refereeComments" />
		<form:hidden path="customerComments" />
		<form:label path="handyComments">
			<spring:message code="notes.write.comment" />:</form:label>
		<form:textarea path="handyComments" />
		<form:errors path="handyComments" />
		<br />

	</security:authorize>

</form:form>

	<button onClick="sendForm()">
		<spring:message code="notes.save" />
	</button>
