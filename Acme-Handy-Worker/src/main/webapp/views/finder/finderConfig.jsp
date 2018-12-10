<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="finder/handyworker/fiderconfig.do" modelAttribute="finder">

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="cacheUpdate"/>
	<form:hidden path="fixUpTask"/>	
	
	<form:label path="keyWord"><spring:message code="finder.keyWord"/></form:label>
	<form:input path="keyword"/>
	<form:errors path="keyWord" cssClass="error"/>
	<br/>
	
	<form:label path="warranty"><spring:message code="finder.warranty"/></form:label>
	<form:input path="warranty"/>
	<form:errors path="warranty" cssClass="error"/>
	<br/>
	
	<form:label path="minPrice"><spring:message code="finder.minPrice"/></form:label>
	<form:input path="minPrice" placeholder="0.0"/>
	<form:errors path="minPrice" cssClass="error"/>
	<br/>
	
	<form:label path="maxPrice"><spring:message code="finder.maxPrice"/></form:label>
	<form:input path="maxPrice" placeholder="1000.0"/>
	<form:errors path="maxPrice" cssClass="error"/>
	<br/>
	
	<form:label path="startDate"><spring:message code="finder.startDate"/>:&nbsp;</form:label>
	<form:input path="startDate"/>
	<form:errors path="startDateTripMin" cssClass="error"/>
	<br/>
	
	<form:label path="endDate"><spring:message code="finder.endDate"/>:&nbsp;</form:label>
	<form:input path="endDate"/>
	<form:errors path="endDate" cssClass="error"/>
	<br/>
	
	<b><form:label path="category"><spring:message code="category"/>:&nbsp;</form:label></b>
	<form:select path="category">
		<form:option label="----" value="0"/>
		<form:options items="${categories}" itemLabel="name" itemValue="id"/>
	</form:select>
	<form:errors path="category" cssClass="error"/>
	<br/>
	
	<input type="submit" name="save" value="<spring:message code="finder.search"/>"/>
	
	<input type="button" name="cancel" value="<spring:message code="finder.cancel" />" onclick="javascript: relativeRedir('welcome/index.jsp');" />
		
</form:form>


<jstl:if test="${!fixuptask.isEmpty()}">
<display:table name="fixuptask" id="row" pagesize="5" class="displaytag" requestURI="${requestURI}">
	
	<display:column>
		<a href="finder/display.do?fixuptaskId=${row.id}"><spring:message code="fixuptask.display"/></a>
	</display:column>
	
	<spring:message var="tickerH" code="fixuptask.ticker"/>
	<display:column property="ticker" title="${tickerH}"/>
	
	<spring:message var="categoryH" code="fixuptask.category"/>
	<display:column property="category.name" title="${categoryH}"/>
	
	<spring:message var="periodStartH" code="fixuptask.periodStart"/>
	<display:column property="periodStart" title="${periodStartH}"/>
	
	<spring:message var="periodEndH" code="fixuptask.periodEnd"/>
	<display:column property="periodStart" title="${periodStartH}"/>
	
	<spring:message var="maxPriceH" code="fixuptask.maxPrice"/>
	<display:column property="maxPrice" title="${maxPriceH}"/>
	
</display:table>
</jstl:if>
