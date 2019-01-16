<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<script type='text/javascript'>
	function addFields(){
		 // Container <div> where dynamic content will be placed
		 var container = document.getElementById("container");
		 // Create an <input> element, set its type and name attributes
		 var input = document.createElement("input");
		 input.type = "text";
		 input.name = "comments";
		 container.appendChild(input);       
	}
</script>

<button type="button" onclick="window.location.href=document.referrer">
	<spring:message code="curricula.back"/>
</button>

<jstl:if test="${personal }">
	<form:form action="curricula/edit/savePersonal.do" modelAttribute="personalRecord">
		<p>
			<spring:message code="curricula.personalRecord.name"/>:
			<form:input path="fullName"/>
			<form:errors path="fullName" cssClass="error"/>
		</p>
		<p>
			<spring:message code="curricula.personalRecord.photo"/>:
			<form:input path="photo"/>
			<form:errors path="photo" cssClass="error"/>
		</p>
		<p>
			<spring:message code="curricula.personalRecord.email"/>:
			<form:input path="email"/>
			<form:errors path="email" cssClass="error"/>
		</p>
		<p>
			<spring:message code="curricula.personalRecord.phoneNumber"/>:
			<form:input path="phoneNumber"/>
			<form:errors path="phoneNumber" cssClass="error"/>
		</p>
		<p>
			<spring:message code="curricula.personalRecord.linkedInUrl"/>:
			<form:input path="linkedInURL"/>
			<form:errors path="linkedInURL" cssClass="error"/>
		</p>
		<p>
			<spring:message code="curricula.comments"/>:
			<button type="button" onClick="addFields()">
				<spring:message code="curricula.edit.addComments"/>
			</button>
    		<div id="container"></div>
    		
			<jstl:forEach items="${personalRecord.comments }" var="comment">
				<input name="comments" value="${comment }"/>
			</jstl:forEach>
			<form:errors path="comments" cssClass="error"/>
		</p>
		<input type="submit" value="<spring:message code='curricula.save'/>"/>
	</form:form>
</jstl:if>
<jstl:if test="${education }">
	<form:form action="curricula/edit/saveEducation.do" modelAttribute="educationRecord">
		<p>
			<spring:message code="curricula.educationRecord.diploma"/>:
			<form:input path="diplomaTitle"/>
			<form:errors path="diplomaTitle" cssClass="error"/>
		</p>
		<p>
			<spring:message code="curricula.educationRecord.institution"/>:
			<form:input path="institution"/>
			<form:errors path="institution" cssClass="error"/>
		</p>
		<p>
			<spring:message code="curricula.educationRecord.start"/>:
			<form:input path="start" placeholder="dd/MM/yyyy HH:mm"/>
			<form:errors path="start" cssClass="error"/>
		</p>
		<p>
			<spring:message code="curricula.educationRecord.end"/>:
			<form:input path="end" placeholder="dd/MM/yyyy HH:mm"/>
			<form:errors path="end" cssClass="error"/>
		</p>
		<p>
			<spring:message code="curricula.attachments"/>:
			<form:input path="attachmentURL" placeholder="dd/MM/yyyy HH:mm"/>
			<form:errors path="attachmentURL" cssClass="error"/>
		</p>
		<p>
			<spring:message code="curricula.edit.addComments"/>:
			<button type="button" onClick="addFields()">
				<spring:message code="warranty.edit.newLaw"/>
			</button>
    		<div id="container"></div>
    		
			<jstl:forEach items="${educationRecord.comments }" var="comment">
				<input name="comments" value="${comment }"/>
			</jstl:forEach>
			<form:errors path="comments" cssClass="error"/>
		</p>
		<input type="submit" value="<spring:message code='curricula.save'/>"/>
	</form:form>
</jstl:if>
<jstl:if test="${professional }">
	<form:form action="curricula/edit/saveEducation.do" modelAttribute="educationRecord">
		<p>
			<spring:message code="curricula.educationRecord.diploma"/>:
			<form:input path="diplomaTitle"/>
			<form:errors path="diplomaTitle" cssClass="error"/>
		</p>
		<p>
			<spring:message code="curricula.educationRecord.institution"/>:
			<form:input path="institution"/>
			<form:errors path="institution" cssClass="error"/>
		</p>
		<p>
			<spring:message code="curricula.educationRecord.start"/>:
			<form:input path="start" placeholder="dd/MM/yyyy HH:mm"/>
			<form:errors path="start" cssClass="error"/>
		</p>
		<p>
			<spring:message code="curricula.educationRecord.end"/>:
			<form:input path="end" placeholder="dd/MM/yyyy HH:mm"/>
			<form:errors path="end" cssClass="error"/>
		</p>
		<p>
			<spring:message code="curricula.attachments"/>:
			<form:input path="attachmentURL" placeholder="dd/MM/yyyy HH:mm"/>
			<form:errors path="attachmentURL" cssClass="error"/>
		</p>
		<p>
			<spring:message code="curricula.edit.addComments"/>:
			<button type="button" onClick="addFields()">
				<spring:message code="warranty.edit.newLaw"/>
			</button>
    		<div id="container"></div>
    		
			<jstl:forEach items="${personalRecord.comments }" var="comment">
				<input name="comments" value="${comment }"/>
			</jstl:forEach>
			<form:errors path="comments" cssClass="error"/>
		</p>
		<input type="submit" value="<spring:message code='curricula.save'/>"/>
	</form:form>
</jstl:if>

