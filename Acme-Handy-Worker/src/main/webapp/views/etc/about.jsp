<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p>Copyright &copy; <jstl:out value="${year}"/> ACME Co.</p>

<jstl:if test="${locale == 'en' }">
	
	<p>This web application was made for the subject <b>Design and Testing 1</b>.<br/>
	<br/>
	The members of the group are:</p>
	 <ul>
	  <li>Garc�a Gran�s, Gonzalo</li>
	  <li>L�pez Carnicer, Jos� Manuel</li>
	  <li>P�rez Guti�rrez, Manuel Cecilio</li>
	  <li>S�nchez Hipona, Antonio</li>
	</ul> 
</jstl:if>
<jstl:if test="${locale == 'es' }">

	<p>Esta aplicaci�n web ha sido hecha para la asignatura <b>Dise�o y Pruebas 1</b>.<br/>
	<br/>
	Los miembros del grupo son:</p>
	<ul>
	  <li>Garc�a Gran�s, Gonzalo</li>
	  <li>L�pez Carnicer, Jos� Manuel</li>
	  <li>P�rez Guti�rrez, Manuel Cecilio</li>
	  <li>S�nchez Hipona, Antonio</li>
	</ul> 
	
</jstl:if>