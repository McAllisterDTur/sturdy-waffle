<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<script>
	function searchByKeyword(e) {
		if (e.keyCode == 13) {
			var keyword = document.getElementById("keyword").value;
			window.location.assign("fixuptask/list.do?keyword=" + keyword);
			return false;
		}
	}
</script>
<input type="text" id="keyword"
	placeholder="<spring:message code="fixuptask.search" />"
	onkeypress="searchByKeyword(event)" />

<display:table name="fixuptask" id="row" pagesize="5" class="displaytag"
	requestURI="${requestURI}">

	<security:authorize access="hasRole('CUSTOMER')">
		<jstl:if test="${requestURI == 'fixuptask/customer/list.do'}">
			<display:column>
				<jstl:if test="${row.publishTime gt date}">
					<a href="fixuptask/customer/edit.do?fixuptaskId=${row.id}"><spring:message
							code="fixuptask.edit" /></a>
				</jstl:if>
			</display:column>
		</jstl:if>
	</security:authorize>

	<display:column>
		<a href="fixuptask/display.do?fixuptaskId=${row.id}"><spring:message
				code="fixuptask.display" /></a>
	</display:column>

	<spring:message var="tickerH" code="fixuptask.ticker" />
	<display:column property="fixuptask.ticker" titleKey="fixuptask.ticker"/>

	<spring:message var="categoryH" code="fixuptask.category" />
	<display:column property="category.name" titleKey="fixuptask.category.name" />

	<spring:message var="periodStartH" code="fixuptask.periodStart" />
	<display:column property="fixuptask.periodStart" titleKey="fixuptask.periodStart"  />

	<spring:message var="periodEndH" code="fixuptask.periodEnd" />
	<display:column property="fixuptask.periodStart" titleKey="fixuptask.periodStart" />

	<spring:message var="maxPriceH" code="fixuptask.maxPrice" />
	<display:column property="fixuptask.maxPrice" titleKey="fixuptask.maxPrice" />
	<display:column>
		<button
			onClick="window.location.href='/Acme-Handy-Worker/fixuptask/display.do?id=${row.id}'">
			<spring:message code="fixuptask.display" />
		</button>
	</display:column>
	
	<security:authorize access="hasRole('CUSTOMER')">
		<display:column>
			<jstl:if test="${row.customer.account.username == principalId}">
				<button
					onClick="window.location.href='/Acme-Handy-Worker/fixuptask/edit.do?id=${row.id}'">
					<spring:message code="fixuptask.edit" />
				</button>
			</jstl:if>
		</display:column>
		<display:column>
			<jstl:if test="${row.customer.account.username == principalId}">
				<button
					onClick="window.location.href='/Acme-Handy-Worker/fixuptask/delete.do?id=${row.id}'">
					<spring:message code="fixuptask.delete" />
				</button>
			</jstl:if>
		</display:column>
	</security:authorize>

</display:table>


