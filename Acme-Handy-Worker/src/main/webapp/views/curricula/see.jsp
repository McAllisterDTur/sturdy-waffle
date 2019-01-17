<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<button type="button" onclick="window.location.href=document.referrer">
	<spring:message code="curricula.back"/>
</button>

<jstl:if test="${logged }">
	<button onClick="window.location.href='profile/curricula/edit/personalRecord.do?id=${personalRecord.id}'">
		<spring:message code="curricula.edit"/>
	</button>
</jstl:if>
<fieldset>
	<legend><spring:message code="curricula.personalRecord"/></legend>
	<img height="250" src="<jstl:out value="${personalRecord.photo }"/>"/><br/>
	<b><spring:message code="curricula.personalRecord.name"/></b>:
	<jstl:out value="${personalRecord.fullName }"/><br/>
	<b><spring:message code="curricula.personalRecord.email"/></b>:
	<jstl:out value="${personalRecord.email }"/><br/>
	<b><spring:message code="curricula.personalRecord.phoneNumber"/></b>:
	<jstl:out value="${personalRecord.phoneNumber }"/><br/>
	<b><spring:message code="curricula.personalRecord.linkedInUrl"/></b>:
	<jstl:out value="${personalRecord.linkedInURL }"/><br/>
	<b><spring:message code="curricula.personalRecord.comments"/></b>:
	<ul>
		<jstl:forEach items="${personalRecord.comments }" var="comment">
			<li><jstl:out value="${comment }"/></li>
		</jstl:forEach>
	</ul>
</fieldset>
<br/>
<jstl:if test="${logged }">
	<button onClick="window.location.href='profile/curricula/edit/addEducationRecord.do'">
		<spring:message code="curricula.add.education"/>
	</button>
</jstl:if>
<fieldset>
	<legend><spring:message code="curricula.educationRecord"/></legend>
	<display:table name="educationRecords" id="row" requestURI="/curricula/see.do">
		<display:column property="diplomaTitle" titleKey="curricula.education.diploma"/>
		<display:column property="institution" titleKey="curricula.education.institution" />
		<display:column property="start" titleKey="curricula.education.start"  />
		<display:column property="end" titleKey="curricula.education.end" />
	</display:table>
</fieldset>
<br/>
<jstl:if test="${logged }">
	<button onClick="window.location.href='profile/curricula/edit/addProfessionalRecord.do'">
		<spring:message code="curricula.add.professional"/>
	</button>
</jstl:if>
<fieldset>
	<legend><spring:message code="curricula.professionalRecord"/></legend>
	<display:table name="profesionalRecords" id="row" requestURI="/curricula/see.do">
		<display:column property="companyName" titleKey="curricula.professional.company"/>
		<display:column property="role" titleKey="curricula.professional.role" />
		<display:column property="start" titleKey="curricula.professional.start"  />
		<display:column property="end" titleKey="curricula.professional.end" />
		<display:column titleKey="curricula.attachments">
			<a href="${row.attachmentURL }"><spring:message code="curricula.link"/></a>
		</display:column>
	</display:table>
</fieldset>
<br/>
<jstl:if test="${logged }">
	<button onClick="window.location.href='profile/curricula/edit/addEndorserRecord.do'">
		<spring:message code="curricula.add.endorser"/>
	</button>
</jstl:if>
<fieldset>
	<legend><spring:message code="curricula.endorserRecord"/></legend>
	<display:table name="endorserRecords" id="row" requestURI="/curricula/see.do">
		<display:column property="endorserName" titleKey="curricula.endorsement.name"/>
		<display:column property="phoneNumber" titleKey="curricula.endorsement.phone" />
		<display:column property="email" titleKey="curricula.endorsement.email"  />
		<display:column titleKey="curricula.endorsement.linkedin">
			<a href="${row.linkedInURL }"><spring:message code="curricula.link"/></a>
		</display:column>
	</display:table>
</fieldset>
<br/>
<jstl:if test="${logged }">
	<button onClick="window.location.href='profile/curricula/edit/addMiscRecord.do'">
		<spring:message code="curricula.add.misc"/>
	</button>
</jstl:if>
<fieldset>
	<legend><spring:message code="curricula.miscRecord"/></legend>
	<display:table name="miscellaneousRecords" id="row" requestURI="/curricula/see.do">
		<display:column property="title" titleKey="curricula.misc.title" />
		<display:column titleKey="curricula.attachments">
			<a href="${row.attachmentURL }"><spring:message code="curricula.link"/></a>
		</display:column>
	</display:table>
</fieldset>

