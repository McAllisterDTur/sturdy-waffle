<%--
 * action-2.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>



<jstl:if test="${logged }">
	<button onClick="window.location.href='profile/edit.do'">
		<spring:message code="profile.edit"/>
	</button>
</jstl:if>

<jstl:if test="${!logged }">
	<security:authorize access="hasRole('ADMIN')">
		<jstl:if test="${actor.banned }">
			<button onClick="window.location.href='profile/administrator/unban.do?id=${actor.id }'">
				<spring:message code="profile.unban"/>
			</button>
		</jstl:if>
		<jstl:if test="${!actor.banned }">
			<button onClick="window.location.href='profile/administrator/ban.do?id=${actor.id }'">
				<spring:message code="profile.ban"/>
			</button>
		</jstl:if>
	</security:authorize>
	
</jstl:if>

<h2><spring:message code="profile.seeing" /> <jstl:out value="${username }"/></h2>

<img src="${actor.photoURL }" alt="<spring:message code="profile.photo" /> <jstl:out value="${username }"/>"/>
<p>
	<b><spring:message code="profile.name" /></b>: 
	<jstl:out value="${actor.surname }"/>, <jstl:out value="${actor.name }"/> <jstl:out value="${actor.middleName }"/>
</p>
<jstl:if test="${endorsable }"><p>
	<b><spring:message code="profile.score" /></b>: 
	<jstl:out value="${score }"/>
</p></jstl:if>
<jstl:if test="${handy }"><p>
	<b><spring:message code="profile.make" /></b>: 
	<jstl:out value="${make }"/>
</p></jstl:if>
<p>
	<b><spring:message code="profile.email" /></b>: 
	<jstl:out value="${actor.email }"/>
</p>
<p>
	<b><spring:message code="profile.phone" /></b>: 
	<jstl:out value="${actor.phone }"/>
</p>
<p>
	<b><spring:message code="profile.address" /></b>: 
	<jstl:out value="${actor.address }"/>
</p>
	
	