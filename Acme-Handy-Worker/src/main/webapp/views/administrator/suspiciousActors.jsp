<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="actors" id="row" class="displaytag" pagesize="6" requestURI="${requestURI }">
	<display:column property="name" titleKey="actor.name" />
	<display:column property="surname" titleKey="actor.surname" />
	<display:column property="email" titleKey="actor.email" />
	<display:column property="phone" titleKey="actor.phone"/>
	<display:column property="address" titleKey="actor.address"/>
	<display:column property="account.username" titleKey="actor.userAccount.name"/>
	<display:column>
		<jstl:if test="${row.banned }">
			<button onClick="window.location.href='profile/administrator/unban.do?id=${row.id }'">
				<spring:message code="profile.unban"/>
			</button>
		</jstl:if>
		<jstl:if test="${!row.banned}">
			<button onClick="window.location.href='profile/administrator/ban.do?id=${row.id }'">
				<spring:message code="profile.ban"/>
			</button>
		</jstl:if>
	</display:column>
</display:table>