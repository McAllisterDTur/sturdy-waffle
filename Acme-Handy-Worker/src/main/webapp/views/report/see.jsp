<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<div>
	<h2><spring:message code="report.rfvd" /></h2>
	<p><jstl:out value="${report.veredict }"/></p>
	<div>
		<jstl:forEach items="${report.messages}" var="msg">
			<p>
				<b><jstl:out value="${msg.authorType}"/> comment #<jstl:out value="${msg.number}"/>:</b> 
				<jstl:out value="${msg.text}"/></p>
		</jstl:forEach>
	</div>
	<div>
		<form:form modelAttribute="comment" action="report/sendComment.do">
			<form:input path="comment" placeholder="<spring:message code="report.wthr" />"/>
			<input type="submit" name="submit" value="<spring:message code="report.send"/>"/>
		</form:form>
	</div>
</div>