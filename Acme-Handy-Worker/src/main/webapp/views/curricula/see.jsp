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
	<button onClick="window.location.href='curricula/edit/personalRecord.do?id=${personalRecord.id}'">
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
	<jstl:out value="${personalRecord.linkedInUrl }"/><br/>
	<b><spring:message code="curricula.personalRecord.comments"/></b>:
	<ul>
		<jstl:forEach items="${personalRecord.comments }" var="comment">
			<li><jstl:out value="${comment }"/></li>
		</jstl:forEach>
	</ul>
</fieldset>
<br/>
<jstl:if test="${logged }">
	<button onClick="window.location.href='curricula/edit/addEducationRecord.do'">
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
		<jstl:if test="${logged }">
			<display:column>
				<button onClick="window.location.href='curricula/edit/deleteEducationRecord.do?id=${row.id}'">
					<spring:message code="curricula.add.education"/>
				</button>
			</display:column>
		</jstl:if>
	</display:table>
</fieldset>
<br/>
<fieldset>
	<legend><spring:message code="curricula.professionalRecord"/></legend>
	<display:table name="professionalRecords" id="row" requestURI="/curricula/see.do">
		<display:column property="diplomaTitle" titleKey="curricula.education.diploma"/>
		<display:column property="institution" titleKey="curricula.education.institution" />
		<display:column property="start" titleKey="curricula.education.start"  />
		<display:column property="end" titleKey="curricula.education.end" />
		<display:column titleKey="curricula.attachments">
			<a href="${row.attachmentURL }"><spring:message code="curricula.link"/></a>
		</display:column>
		<jstl:if test="${logged }">
			<display:column>
				<button onClick="window.location.href='curricula/edit/deleteEducationRecord.do?id=${row.id}'">
					<spring:message code="curricula.add.education"/>
				</button>
			</display:column>
		</jstl:if>
	</display:table>
</fieldset>

