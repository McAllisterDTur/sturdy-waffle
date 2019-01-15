<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%
	String s = request.getUserPrincipal() != null ? request.getUserPrincipal().getName() : "";
%>
<jstl:set var="principal" value="<%=s%>" />

<div>
	<jstl:if test="${not empty messageCode}">
		<h4>
			<spring:message code="${messageCode}" />
		</h4>
	</jstl:if>
	<display:table name="boxes" id="box" requestURI="${requestURI}"
		pagesize="10">
		<display:column property="name" titleKey="box.name" />

		<display:column>
			<button
				onClick="window.location.href='message/list.do?boxId=${box.id}'">
				<spring:message code="box.seeM" />
			</button>
		</display:column>

		<display:column titleKey="box.edit">
			<jstl:if test="${box.deleteable == true}">

				<button
					onClick="window.location.href='box/edit.do?boxId=${box.id}'">
					<spring:message code="box.edit" />
				</button>
			</jstl:if>
		</display:column>
		<display:column titleKey="box.delete">
			<jstl:if test="${box.deleteable == true}">
				<button
					onClick="window.location.href='box/delete.do?boxId=${box.id}'">
					<spring:message code="box.delete" />
				</button>
			</jstl:if>
		</display:column>

	</display:table>
</div>
