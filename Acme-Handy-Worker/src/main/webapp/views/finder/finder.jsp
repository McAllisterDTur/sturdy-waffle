<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="finder/finder.do" modelAttribute="finder">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="cacheUpdate" />
	<form:hidden path="fixUpTask" />
	<form:hidden path="worker" />

	<form:label path="keyWord">
		<spring:message code="finder.keyword" />: </form:label>
	<form:input path="keyWord" />
	<form:errors path="keyWord" cssClass="error" />
	<br />

	<form:label path="warranty">
		<spring:message code="finder.warranty" />: </form:label>
	<form:input path="warranty" />
	<form:errors path="warranty" cssClass="error" />
	<br />

	<form:label path="minPrice">
		<spring:message code="finder.minprice" />: </form:label>
	<form:input path="minPrice" placeholder="0.0" />
	<form:errors path="minPrice" cssClass="error" />
	<br />

	<form:label path="maxPrice">
		<spring:message code="finder.maxprice" />: </form:label>
	<form:input path="maxPrice" placeholder="1000.0" />
	<form:errors path="maxPrice" cssClass="error" />
	<br />

	<form:label path="startDate">
		<spring:message code="finder.startdate" />:&nbsp;</form:label>
	<form:input path="startDate" />
	<form:errors path="startDate" cssClass="error" />
	<br />

	<form:label path="endDate">
		<spring:message code="finder.enddate" />:&nbsp;</form:label>
	<form:input path="endDate" />
	<form:errors path="endDate" cssClass="error" />
	<br />

	<form:label path="category">
		<spring:message code="finder.category" />:&nbsp;</form:label>
	<form:select path="category">
		<form:option label="----" value="" />
		<jstl:if test="${pageContext.response.locale.language == 'en'}">
			<form:options items="${categories}" itemLabel="nameEn" itemValue="nameEn" />
		</jstl:if>
		<jstl:if test="${pageContext.response.locale.language == 'es'}">
			<form:options items="${categories}" itemLabel="name" itemValue="name" />
		</jstl:if>
	</form:select>
	<form:errors path="category" cssClass="error" />
	<br />

	<input type="submit" name="save"
		value="<spring:message code="finder.search"/>" />

</form:form>

<display:table name="finder.fixUpTask" id="row" pagesize="5"
	requestURI="${requestURI}">

	<display:column property="ticker" titleKey="finder.fixuptask.ticker" />
	<display:column property="category.name"
		titleKey="finder.fixuptask.category" />

	<display:column property="periodStart"
		titleKey="finder.fixuptask.periodStart" />

	<display:column property="periodStart"
		titleKey="finder.fixuptask.periodStart" />

	<display:column property="maxPrice"
		titleKey="finder.fixuptask.maxPrice" />

	<display:column>
		<button
			onClick="window.location.href='/Acme-Handy-Worker/fixuptask/customer,handyworker/display.do?fixuptaskId=${row.id}'">
			<spring:message code="finder.fixuptask.display" />
		</button>
	</display:column>

</display:table>


