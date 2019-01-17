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
	<form:form action="profile/curricula/edit/savePersonal.do" modelAttribute="personalRecord">
		<form:hidden path="id"/>
		<form:hidden path="version"/>
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
	<form:form action="profile/curricula/edit/saveEducation.do" modelAttribute="educationRecord">
		<p>
			<spring:message code="curricula.education.diploma"/>:
			<form:input path="diplomaTitle"/>
			<form:errors path="diplomaTitle" cssClass="error"/>
		</p>
		<p>
			<spring:message code="curricula.education.institution"/>:
			<form:input path="institution"/>
			<form:errors path="institution" cssClass="error"/>
		</p>
		<p>
			<spring:message code="curricula.education.start"/>:
			<form:input path="start" placeholder="dd/MM/yyyy HH:mm"/>
			<form:errors path="start" cssClass="error"/>
		</p>
		<p>
			<spring:message code="curricula.education.end"/>:
			<form:input path="end" placeholder="dd/MM/yyyy HH:mm"/>
			<form:errors path="end" cssClass="error"/>
		</p>
		<p>
			<spring:message code="curricula.attachments"/>:
			<form:input path="attachmentURL"/>
			<form:errors path="attachmentURL" cssClass="error"/>
		</p>
		<p>
			<spring:message code="curricula.edit.addComments"/>:
			<button type="button" onClick="addFields()">
				<spring:message code="curricula.edit.addComments"/>
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
	<form:form action="profile/curricula/edit/saveProfessional.do" modelAttribute="professionalRecord">
		<p>
			<spring:message code="curricula.professional.company"/>:
			<form:input path="companyName"/>
			<form:errors path="companyName" cssClass="error"/>
		</p>
		<p>
			<spring:message code="curricula.professional.role"/>:
			<form:input path="role"/>
			<form:errors path="role" cssClass="error"/>
		</p>
		<p>
			<spring:message code="curricula.professional.start"/>:
			<form:input path="start" placeholder="dd/MM/yyyy HH:mm"/>
			<form:errors path="start" cssClass="error"/>
		</p>
		<p>
			<spring:message code="curricula.professional.end"/>:
			<form:input path="end" placeholder="dd/MM/yyyy HH:mm"/>
			<form:errors path="end" cssClass="error"/>
		</p>
		<p>
			<spring:message code="curricula.attachments"/>:
			<form:input path="attachmentURL"/>
			<form:errors path="attachmentURL" cssClass="error"/>
		</p>
		<p>
			<spring:message code="curricula.edit.addComments"/>:
			<button type="button" onClick="addFields()">
				<spring:message code="curricula.edit.addComments"/>
			</button>
    		<div id="container"></div>
    		
			<jstl:forEach items="${professionalRecord.comments }" var="comment">
				<input name="comments" value="${comment }"/>
			</jstl:forEach>
			<form:errors path="comments" cssClass="error"/>
		</p>
		<input type="submit" value="<spring:message code='curricula.save'/>"/>
	</form:form>
</jstl:if>
<jstl:if test="${endorser }">
	<form:form action="profile/curricula/edit/saveEndorser.do" modelAttribute="endorserRecord">
		<p>
			<spring:message code="curricula.endorsement.name"/>:
			<form:input path="endorserName"/>
			<form:errors path="endorserName" cssClass="error"/>
		</p>
		<p>
			<spring:message code="curricula.endorsement.phone"/>:
			<form:input path="phoneNumber"/>
			<form:errors path="phoneNumber" cssClass="error"/>
		</p>
		<p>
			<spring:message code="curricula.endorsement.email"/>:
			<form:input path="email"/>
			<form:errors path="email" cssClass="error"/>
		</p>
		<p>
			<spring:message code="curricula.endorsement.linkedin"/>:
			<form:input path="linkedInURL"/>
			<form:errors path="linkedInURL" cssClass="error"/>
		</p>
		<p>
			<spring:message code="curricula.edit.addComments"/>:
			<button type="button" onClick="addFields()">
				<spring:message code="curricula.edit.addComments"/>
			</button>
    		<div id="container"></div>
    		
			<jstl:forEach items="${endorserRecord.comments }" var="comment">
				<input name="comments" value="${comment }"/>
			</jstl:forEach>
			<form:errors path="comments" cssClass="error"/>
		</p>
		<input type="submit" value="<spring:message code='curricula.save'/>"/>
	</form:form>
</jstl:if>
<jstl:if test="${miscellaneous }">
	<form:form action="profile/curricula/edit/saveMiscellaneous.do" modelAttribute="miscellaneousRecord">
		<p>
			<spring:message code="curricula.misc.title"/>:
			<form:input path="title"/>
			<form:errors path="title" cssClass="error"/>
		</p>
		<p>
			<spring:message code="curricula.attachments"/>:
			<form:input path="attachmentURL"/>
			<form:errors path="attachmentURL" cssClass="error"/>
		</p>
		<p>
			<spring:message code="curricula.edit.addComments"/>:
			<button type="button" onClick="addFields()">
				<spring:message code="curricula.edit.addComments"/>
			</button>
    		<div id="container"></div>
    		
			<jstl:forEach items="${miscellaneousRecord.comments }" var="comment">
				<input name="comments" value="${comment }"/>
			</jstl:forEach>
			<form:errors path="comments" cssClass="error"/>
		</p>
		<input type="submit" value="<spring:message code='curricula.save'/>"/>
	</form:form>
</jstl:if>

