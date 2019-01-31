<%@page
	import="org.springframework.web.servlet.support.RequestContextUtils"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<p><spring:message code="success.title"/></p>
<p><a href="#"><spring:message code="success.welcome"/></a> | <a href="configuration/administrator/customize.do"><spring:message code="success.edit"/></a></p>