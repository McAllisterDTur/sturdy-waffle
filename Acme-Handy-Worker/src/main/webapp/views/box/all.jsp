<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<h1><spring:message code="box.titl" /> </h1>
<div>
	<display:table name="boxes" id="box" requestURI="${requestURI}" pagesize="10">
			<display:column property="name" titleKey="boxes.name" />
			<display:column titleKey="boxes.name">
				<a href="messages/all.do?id=${row.box.id}">
					<spring:message code="box.seeM" /> 
				</a>
		</display:column>
	</display:table>
</div>
