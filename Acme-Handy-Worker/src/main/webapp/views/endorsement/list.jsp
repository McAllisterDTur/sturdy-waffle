<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<jstl:if test="${!forMe }">
	<button onClick="window.location.href='endorsement/handyworker,customer/receivedEndorsements.do'">
		<spring:message code="endorsement.receivedEndorsements" />
	</button>
</jstl:if>
<jstl:if test="${forMe }">
	<button onClick="window.location.href='endorsement/handyworker,customer/sentEndorsements.do'">
		<spring:message code="endorsement.sentEndorsements" />
	</button>
</jstl:if>
<button onClick="window.location.href='endorsement/handyworker,customer/new.do'">
	<spring:message code="endorsement.new" />
</button>

<jstl:if test="${forMe }">
	<display:table name="endorsements" id="endorsement" requestURI="${requestURI}" pagesize="5">
		<display:column titleKey="endorsement.sender">
			<a href="profile/seeId.do?id=${endorsement.sender.id }">
				<jstl:out value="${endorsement.sender.account.username}" />
			</a>
		</display:column>
		<display:column property="writeTime" titleKey="endorsement.written" />
		<display:column>
			<button onClick="window.location.href='endorsement/handyworker,customer/see.do?id=${endorsement.id}'">
				<spring:message code="endorsement.seeComment" />
			</button>
		</display:column>
	</display:table>
</jstl:if>
<jstl:if test="${!forMe }">
	<display:table name="endorsements" id="endorsement" requestURI="${requestURI}" pagesize="5">
		<display:column titleKey="endorsement.receiver">
			<a href="profile/seeId.do?id=${endorsement.reciever.id }">
				<jstl:out value="${endorsement.reciever.account.username}" />
			</a>
		</display:column>
		<display:column property="writeTime" titleKey="endorsement.written" />
		<display:column>
			<button onClick="window.location.href='endorsement/handyworker,customer/see.do?id=${endorsement.id}'">
				<spring:message code="endorsement.seeComment" />
			</button>
		</display:column>
		<display:column>
			<button onClick="window.location.href='endorsement/handyworker,customer/edit.do?id=${endorsement.id}'">
				<spring:message code="endorsement.edit" />
			</button>
		</display:column>
		<display:column>
			<button onClick="window.location.href='endorsement/handyworker,customer/delete.do?id=${endorsement.id}'">
				<spring:message code="endorsement.delete" />
			</button>
		</display:column>
	</display:table>
</jstl:if>