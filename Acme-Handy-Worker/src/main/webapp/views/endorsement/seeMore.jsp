<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<div>
	<button onClick="window.location.href='endorsement/handyworker,customer/receivedEndorsements.do'">
		<spring:message code="endorsement.see.back" />
	</button>
	<p>
		<b><spring:message code="edorsement.see.sender"/></b>: 
		<a href="profile/seeId.do?id=${senderid}">
			<jstl:out value="${endorsement.sender.account.username}"/>
		</a>
	</p>
	<p>
		<b><spring:message code="edorsement.see.receiver"/></b>: 
		<a href="profile/seeId.do?id=${receiverid}">
			<jstl:out value="${endorsement.reciever.account.username}"/>
		</a>
	</p>
	<p>
		<b><spring:message code="edorsement.see.comment"/></b>:
		<jstl:out value="${endorsement.comment}"/>
	</p>
	<p>
		<b><spring:message code="edorsement.see.writeTime"/></b>:
		<jstl:out value="${endorsement.writeTime}"/>
	</p>
	<jstl:if test="${mine }">
		<p>
			<button onClick="window.location.href='endorsement/handyworker,customer/edit.do?id=${endorsement.id}'">
				<spring:message code="endorsement.edit" />
			</button>
			<button onClick="window.location.href='endorsement/handyworker,customer/delete.do?id=${endorsement.id}'">
				<spring:message code="endorsement.delete" />
			</button>
		</p>
	</jstl:if>
</div>