<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<h3><b><spring:message code="fixuptask.ticker"/>:&nbsp;</b><jstl:out value="${fixuptask.ticker}"/></h3>
<spring:message var="patternDate" code="event.pattern.date" />

<b><spring:message code="fixuptask.publishTime"/>:&nbsp;</b><jstl:out value="${fixuptask.publishTime}"/>
<br/>

<b><spring:message code="fixuptask.description"/>:&nbsp;</b><jstl:out value="${fixuptask.description}"/>
<br/>

<b><spring:message code="fixuptask.address"/>:&nbsp;</b><jstl:out value="${fixuptask.address}"/>
<br/>

<b><spring:message code="fixuptask.category"/>:&nbsp;</b><a href="fixuptask/list-byCategoryId.do?categoryId=${fixuptask.category.id}"><jstl:out value="${fixuptask.category.name}"/></a>
<br/>

<b><spring:message code="fixuptask.periodStart"/>:&nbsp;</b><jstl:out value="${fixuptask.periodStart}"/>
<br/>

<b><spring:message code="fixuptask.periodEnd"/>:&nbsp;</b><jstl:out value="${fixuptask.periodEnd}"/>
<br/>

<b><spring:message code="fixuptask.maxPrice"/>:&nbsp;</b><jstl:out value="${fixuptask.maxPrice}" />
<br/>

<b><spring:message code="fixuptask.customer"/>:&nbsp;</b><jstl:out value="${fixuptask.customer.name}"/>
<br/>

<b><spring:message code="fixuptask.warranty"/>:&nbsp;</b><a href="warranty/display.do?warrantyId=${fixuptask.warranty.id}"><jstl:out value="${fixuptask.fixuptask.title}"/></a>
<br/>

