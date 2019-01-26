<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

	<script type='text/javascript'>
        function addSpamWord(){
	        // Container <div> where dynamic content will be placed
	        var container = document.getElementById("containerSpam");
	        // Create an <input> element, set its type and name attributes
	        var input = document.createElement("input");
	        input.type = "text";
	        input.name = "spamWords";
	        container.appendChild(input);       
        }
        
        function addPositiveWord(){
	        // Container <div> where dynamic content will be placed
	        var container = document.getElementById("containerPositive");
	        // Create an <input> element, set its type and name attributes
	        var input = document.createElement("input");
	        input.type = "text";
	        input.name = "positveWords";
	        container.appendChild(input);       
        }
        
        function addNegativeWord(){
	        // Container <div> where dynamic content will be placed
	        var container = document.getElementById("containerNegative");
	        // Create an <input> element, set its type and name attributes
	        var input = document.createElement("input");
	        input.type = "text";
	        input.name = "negativeWords";
	        container.appendChild(input);       
        }
        
        function addCardMaker(){
	        // Container <div> where dynamic content will be placed
	        var container = document.getElementById("containerCardMaker");
	        // Create an <input> element, set its type and name attributes
	        var input = document.createElement("input");
	        input.type = "text";
	        input.name = "cardMaker";
	        container.appendChild(input);       
        }
    </script>

<button type="button" onclick="window.location.href=document.referrer">
	<spring:message code="configuration.back"/>
</button>
<form:form modelAttribute="configuration" action="configuration/administrator/save.do">
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<fieldset>
		<legend><spring:message code="configuration.finder"/></legend>
		<p>
			<spring:message code="configuration.cacheTime"/>: 
			<form:input path="cacheTime"/>
			<form:errors path="cacheTime" cssClass="error"/>
		</p>
		<p>
			<spring:message code="configuration.finderResults"/>: 
			<form:input path="finderResults"/>
			<form:errors path="finderResults" cssClass="error"/>
		</p>
	</fieldset>
	<p>
		<spring:message code="configuration.systemName"/>: 
		<form:input path="nameSystem"/>
		<form:errors path="nameSystem" cssClass="error"/>
	</p>
	<p>
		<spring:message code="configuration.bannerURL"/>: 
		<form:input path="bannerURL"/>
		<form:errors path="bannerURL" cssClass="error"/>
	</p>
	<p>
		<spring:message code="configuration.vat"/>: 
		<form:input path="vat"/>
		<form:errors path="vat" cssClass="error"/>
	</p>
	<p>
		<spring:message code="configuration.welcomeEN"/>: 
		<form:input path="welcomeEN"/>
		<form:errors path="welcomeEN" cssClass="error"/>
	</p>
	<p>
		<spring:message code="configuration.welcomeES"/>: 
		<form:input path="welcomeSP"/>
		<form:errors path="welcomeSP" cssClass="error"/>
	</p>
	<p>
		<spring:message code="configuration.spamWords"/>:
		<button type="button" onClick="addSpamWord()">
			<spring:message code="configuration.newSpamWord"/>
		</button>
   		<div id="containerSpam"></div>
   		
		<jstl:forEach items="${configuration.spamWords }" var="spamWord">
			<input name="spamWords" value="${spamWord }"/>
		</jstl:forEach>
		<form:errors path="spamWords" cssClass="error"/>
	</p>
	<p>
		<spring:message code="configuration.countryCode"/>: 
		<form:input path="countryCode"/>
		<form:errors path="countryCode" cssClass="error"/>
	</p>
	<p>
		<spring:message code="configuration.positiveWords"/>:
		<button type="button" onClick="addPositiveWord()">
			<spring:message code="configuration.newPositiveWord"/>
		</button>
   		<div id="containerPositive"></div>
   		
		<jstl:forEach items="${configuration.positiveWords }" var="positiveWord">
			<input name="positiveWords" value="${positiveWord }"/>
		</jstl:forEach>
		<form:errors path="positiveWords" cssClass="error"/>
	</p>
	<p>
		<spring:message code="configuration.negativeWords"/>:
		<button type="button" onClick="addNegativeWord()">
			<spring:message code="configuration.newNegativeWord"/>
		</button>
   		<div id="containerNegative"></div>
   		
		<jstl:forEach items="${configuration.negativeWords }" var="negativeWord">
			<input name="negativeWords" value="${negativeWord }"/>
		</jstl:forEach>
		<form:errors path="negativeWords" cssClass="error"/>
	</p>
		<p>
		<spring:message code="configuration.cardMaker"/>:
		<button type="button" onClick="addCardMaker()">
			<spring:message code="configuration.newCardMaker"/>
		</button>
   		<div id="containerCardMaker"></div>
   		
		<jstl:forEach items="${configuration.cardMaker }" var="maker">
			<input name="cardMaker" value="${maker }"/>
		</jstl:forEach>
		<form:errors path="cardMaker" cssClass="error"/>
	</p>
	
	<span class="error"><jstl:out value="${wrong }"/></span><br/>
	<input type="submit" value="<spring:message code="configuration.send"/>"/>
	
</form:form>