<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<jstl:set var="msg"><spring:message code='complaint.edit.confirm'/></jstl:set>
<jstl:set var="msgAlert"><spring:message code='complaint.edit.asDraft'/></jstl:set>

<script type='text/javascript'>
	function addFields(){
		// Container <div> where dynamic content will be placed
		var container = document.getElementById("container");
		// Create an <input> element, set its type and name attributes
		var input = document.createElement("input");
		input.type = "text";
		input.name = "law";
		container.appendChild(input);       
	}
</script>
<jstl:if test="${lang == 'en' }">
	<script type='text/javascript'>	
		function sendForm(){
			if(confirm("Do you want to send this complaint?\nThis action can't be undone.")){
				document.getElementById("complaintForm").submit();
			} else {
				alert("The complaint was saved as a draft, and it's not public.\nYou can edit it when you want");
				document.getElementById("complaintForm").action = "complaint/customer/saveDrafted.do";
				document.getElementById("complaintForm").submit();
			}
		}
	</script>
</jstl:if>
<jstl:if test="${lang == 'es' }">
	<script type='text/javascript'>	
		function sendForm(){
			if(confirm("¿Quieres enviar esta queja?\nEsta acción no se puede deshacer.")){
				document.getElementById("complaintForm").submit();
			} else {
				alert("La queja se guardó como borrador y no es pública.\nPuedes editarla cuando quieras.");
				document.getElementById("complaintForm").action = "complaint/customer/saveDrafted.do";
				document.getElementById("complaintForm").submit();
			}
		}
	</script>
</jstl:if>

<form:form id="complaintForm" modelAttribute="complaint" action="complaint/customer/saveFinal.do" method="POST">
	<p>
		<spring:message code="complaint.edit.fixUp"/>: 
		<form:select path="fixUpTask">
			<form:option value="0" label="-------"/>
			<form:options items="${futs }" itemLabel="description" itemValue="id" />				
		</form:select>
		<form:errors path="fixUpTask" cssClass="error"/>
	</p>
	<p>
		<spring:message code="complaint.edit.description"/>:
		<form:input path="description"/>
		<form:errors path="description" cssClass="errors" />
	</p>
	<p>
		<spring:message code="complaint.edit.attachments"/>:
		<button type="button" onClick="addFields()">
			<spring:message code="complaint.edit.newAttachment"/>
		</button>
   		<div id="container"></div>
   		
		<jstl:forEach items="${complaint.attachments }" var="atch">
			<input name="attachments" value="${atch }"/>
		</jstl:forEach>
		<form:errors path="attachments" cssClass="error"/>
	</p>
	
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	
	<button onClick="sendForm()">
		<spring:message code="complaint.edit.send" />
	</button>
</form:form>