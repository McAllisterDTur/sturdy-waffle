<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table pagesize="5" name="tutorials" id="row" requestURI="tutorial/all.do">

	<display:column property="title" titleKey="tutorial.title"></display:column>
	<display:column property="worker.make" titleKey="tutorial.handyworker"></display:column>
	<display:column property="summary" titleKey="tutorial.summary"></display:column>
	<display:column property="lastTimeUpdated" titleKey="tutorial.lastupdate"></display:column>
	
</display:table>