<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<% String s = request.getUserPrincipal() != null ? request.getUserPrincipal().getName() :""; %>
<jstl:set var="principal" value="<%= s %>"/>

<p style="font-size: 2em"><strong><spring:message	code="tutorial.title" />:</strong> <jstl:out value='${tutorial.title}'></jstl:out></p>
<p style="font-size: 1.5em"><strong><spring:message	code="tutorial.summary" />:</strong> <jstl:out value='${tutorial.summary}'></jstl:out></p>


<jstl:if test="${tutorial.worker.account.username == principal}">
	<script type="text/javascript">
		function add(id){
			var picture = document.getElementById('picture').value;
			window.location.href='/Acme-Handy-Worker/tutorial/pictures/handyworker/add.do?id=' + id + '&picture=' + picture;
		}
	</script>
	<input type="text" id="picture" placeholder="Picture url"/>
	<button name="picture" onClick="add(${tutorial.id})">add</button>
</jstl:if>
<display:table pagesize="5" name="tutorial.photoURL" id="row" requestURI="${requestURI}">
	<display:column>
		<img class="picture" src="${row }" alt="Imagen del tutorial"/>
	</display:column>
	<security:authorize access="hasRole('HANDYWORKER')">
	<jstl:if test="${tutorial.worker.account.username == principal}">
		<display:column>
			<button onClick="window.location.href='/Acme-Handy-Worker/tutorial/pictures/handyworker/delete.do?id=${tutorial.id }&picture=${row}'"><spring:message code="tutorial.pictures.delete"/></button>
		</display:column>
	</jstl:if>
	</security:authorize>
</display:table>
