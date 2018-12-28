<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<input type="hidden" id="message" value="<spring:message code="complaint.edit.confirm"/>"/>
<input type="hidden" id="msgAlert" value="<spring:message code="complaint.edit.asDraft"/>"/>

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
	
	function sendForm(){
		var msg = document.getElementById("message");
		var msgAlert = document.getElementById("msgAlert");
		if(confirm(msg)){
			document.getElementById("complaintForm").submit();
		} else {
			alert(msgAlert);
			document.getElementById("complaintForm").action = "complaint/customer/saveDraft.do";
			document.getElementById("complaintForm").submit();
		}
	}
</script>

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