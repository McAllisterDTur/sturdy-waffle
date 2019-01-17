<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('ADMIN')">

	<script type='text/javascript'>
        function addFields(){
	        // Container <div> where dynamic content will be placed
	        var container = document.getElementById("container");
	        // Create an <input> element, set its type and name attributes
	        var input = document.createElement("input");
	        input.type = "text";
	        input.name = "law";
	        container.appendChild(input);       
        }
    </script>

	<button onClick="window.location.href='warranty/administrator/list.do'">
		<spring:message code="warranty.back"/>
	</button>
	
	<jstl:if test="${not empty success }">
		<fieldset>
			<legend><spring:message code="warranty.message"/></legend>
			<jstl:if test="${not success }">
				<spring:message code="warranty.edit.error"/>
			</jstl:if>
		</fieldset>
	</jstl:if>
	
	<b><spring:message code="warranty.edit.must"/></b>
		
	<form:form modelAttribute="warranty" action="warranty/administrator/save.do">
		<p>
			<spring:message code="warranty.edit.title"/>: 
			<form:input path="title"/>
			<form:errors path="title" cssClass="error"/>
		</p>
		<p>
			<spring:message code="warranty.edit.terms"/>: 
			<form:input path="terms"/>
			<form:errors path="terms" cssClass="error"/>
		</p>
		<p>
			<spring:message code="warranty.edit.law"/>:
			<button type="button" onClick="addFields()">
				<spring:message code="warranty.edit.newLaw"/>
			</button>
    		<div id="container"></div>
    		
			<jstl:forEach items="${warranty.law }" var="lawItem">
				<input name="law" value="${lawItem }"/>
			</jstl:forEach>
			<form:errors path="law" cssClass="error"/>
		</p>
		<form:hidden path="draft" value="false" />
		<form:hidden path="id" />
		<form:hidden path="version" />
		<input type="submit" name="saveFinal" value="<spring:message code="warranty.edit.final"/>"/>
		<input type="submit" name="saveDraft" value="<spring:message code="warranty.edit.draft"/>"/>
	</form:form>
	
</security:authorize>