<%--
 * footer.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 *
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<jsp:useBean id="date" class="java.util.Date" />

<hr />
<p>
	<a href="etc/license.do"><spring:message code="master.page.footer.license"/></a> |
	<a href="etc/about.do"><spring:message code="master.page.footer.about"/></a> |
	<a href="http://www.us.es"><spring:message code="master.page.footer.us"/></a> |
	<a href="mailto:contact.hw@acme.co"><spring:message code="master.page.footer.contact"/></a>
</p>
<b>Copyright &copy; <fmt:formatDate value="${date}" pattern="yyyy" /> ${namesystem}</b>
