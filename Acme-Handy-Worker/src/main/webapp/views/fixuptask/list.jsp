<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<% String s = request.getUserPrincipal() != null ? request.getUserPrincipal().getName() :""; %>
<jstl:set var="principal" value="<%= s %>"/>

<display:table name="fixuptasks" id="row" pagesize="5" requestURI="${requestURI}">
	
	<security:authorize access="hasRole('CUSTOMER')">
		<jstl:if test="${row.customer.account.username == principal}">
			<display:column>
				<jstl:if test="${row.publishTime gt date}">
					<a href="fixuptask/customer/edit.do?fixuptaskId=${row.id}"><spring:message
							code="fixuptask.edit" /></a>
				</jstl:if>
			</display:column>
		</jstl:if>
	</security:authorize>
	
	

	<display:column property="ticker" titleKey="fixuptask.ticker"/>
	<display:column property="category.name" titleKey="fixuptask.category" />

	<display:column property="periodStart" titleKey="fixuptask.periodStart"  />

	<display:column property="periodStart" titleKey="fixuptask.periodStart" />

	<display:column property="maxPrice" titleKey="fixuptask.maxPrice" />
	
	<display:column>
		<button onClick="window.location.href='Acme-Handy-Worker/fixuptask/customer,handyworker/display.do?fixuptaskId=${row.id}'">
			<spring:message code="fixuptask.display"/>
		</button>
	</display:column>
	
	<security:authorize access="hasRole('CUSTOMER')">
		<display:column>
			<jstl:if test="${row.customer.account.username == principal}">
				<button
					onClick="window.location.href='/Acme-Handy-Worker/fixuptask/edit.do?id=${row.id}'">
					<spring:message code="fixuptask.edit" />
				</button>
			</jstl:if>
		</display:column>
		<display:column>
			<jstl:if test="${row.customer.account.username == principal}">
				<button onClick="window.location.href='/Acme-Handy-Worker/fixuptask/delete.do?id=${row.id}'">
					<spring:message code="fixuptask.delete" />
				</button>
			</jstl:if>
		</display:column>
	</security:authorize>
</display:table>


	





