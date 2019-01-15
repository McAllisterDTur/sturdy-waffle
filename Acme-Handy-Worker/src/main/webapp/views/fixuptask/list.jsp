<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('HANDYWORKER')">
<script type="text/javascript">
	function search(){
		var keyword = document.getElementById('keyword').value;
		window.location.href='fixuptask/handyworker/list.do?keyword=' + keyword;
	}
</script>
<input type="text" id="keyword" placeholder="Keyword"/>
<button name="keyword" onClick="search()">
	<spring:message code="fixuptask.search" />
</button>
</security:authorize>

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

	<display:column titleKey="fixuptask.maxPrice" >
		<jstl:out value="${row.maxPrice }" />(<jstl:out value="${row.maxPrice *(1+(vat/100))}" />)
	</display:column>

	<display:column>
		<button onClick="window.location.href='fixuptask/customer,handyworker/display.do?fixuptaskId=${row.id}'">
			<spring:message code="fixuptask.display"/>
		</button>
	</display:column>

	<security:authorize access="hasRole('HANDYWORKER')">

		<display:column>
			<button
					onClick="window.location.href='application/handyworker/create.do?fixuptaskId=${row.id}'">
					<spring:message code="fixuptask.apply" />
				</button>
		</display:column>

	</security:authorize>

	<security:authorize access="hasRole('CUSTOMER')">
		<display:column>
			<jstl:if test="${row.customer.account.username == principal}">
				<button
					onClick="window.location.href='fixuptask/customer/edit.do?fixuptaskId=${row.id}'">
					<spring:message code="fixuptask.edit" />
				</button>
			</jstl:if>
		</display:column>
		<display:column>
		<jstl:if test="${row.customer.account.username == principal}">
			<button onClick="window.location.href='application/customer,handyworker/list.do?fixuptaskId=${row.id}'">
				<spring:message code="fixuptask.applications"/>
			</button>
		</jstl:if>
		</display:column>
		<display:column>
			<jstl:if test="${row.customer.account.username == principal}">
				<button onClick="window.location.href='fixuptask/customer/delete.do?fixuptaskId=${row.id}'">
					<spring:message code="fixuptask.delete" />
				</button>
			</jstl:if>
		</display:column>
	</security:authorize>
</display:table>
