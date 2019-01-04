<%--
 * header.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div>
	<img src="${bannerURL}" alt="Welcome to ACME Handy-Worker" height="200">
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<li><a class="fNiv" href="/Acme-Handy-Worker/"><spring:message code="master.page.home" /></a></li>
		<li>
			<a class="fNiv" href="tutorial/all.do"><spring:message code="master.page.tutorial" /></a>
			<security:authorize access="hasRole('HANDYWORKER')">
				<ul>
					<li class="arrow"></li>
					<li><a href="handyworker/myTutorials.do"><spring:message code="master.page.handy.myTutorials" /></a></li>
					<li><a href="tutorial/all.do"><spring:message code="master.page.handy.allTutorials" /></a></li>
					<li><a href="tutorial/create.do"><spring:message code="master.page.handy.createTut" /></a></li>					
				</ul>
			</security:authorize>
			<security:authorize access="hasRole('SPONSOR')">
				<ul>
					<li class="arrow"></li>
					<li><a href="tutorial/sponsor.do"><spring:message code="master.page.sponsor.tutorial" /></a></li>			
				</ul>
			</security:authorize>
			</li>
		
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"><spring:message	code="master.page.administrator.acandw" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="administrator/register.do"><spring:message code="master.page.administrator.register" /></a></li>
					<li><a href="warranty/administrator/list.do"><spring:message code="master.page.administrator.warrant" /></a></li>
					<li><a href="category/administrator/list.do"><spring:message code="master.page.administrator.categor" /></a></li>					
				</ul>
			</li>
			
			<li><a class="fNiv"><spring:message	code="master.page.administrator.dandt" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="administrator/statistics.do"><spring:message code="master.page.administrator.stats" /></a></li>
					<li><a href="administrator/configuration.do"><spring:message code="master.page.administrator.config" /></a></li>
					<li><a href="administrator/categories.do"><spring:message code="master.page.administrator.categor" /></a></li>					
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('HANDYWORKER')">
			<li><a class="fNiv"><spring:message	code="master.page.handy.futandapp" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="application/myApps.do"><spring:message code="master.page.handy.myApps" /></a></li>
					<li><a href="fixuptask/handyworker/list.do"><spring:message code="master.page.handy.list" /></a></li>
					<li><a href="finder/update.do"><spring:message code="master.page.handy.finder" /></a></li>					
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('CUSTOMER')">
			<li><a class="fNiv"><spring:message	code="master.page.customer.futsandcomp" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="fixuptask/customer/list.do"><spring:message code="master.page.customer.myfuts" /></a></li>
					<li><a href="fixuptask/customer/create.do"><spring:message code="master.page.customer.newfuts" /></a></li>
					<li><a href="complaint/customer/finalComplaints.do"><spring:message code="master.page.customer.mycomps" /></a></li>
					<li><a href="complaint/customer/new.do"><spring:message code="master.page.customer.newcomp" /></a></li>				
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('REFEREE')">
			<li><a class="fNiv"><spring:message	code="master.page.referee.compandreps" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="complaint/referee/unassignedComplaints.do"><spring:message code="master.page.referee.allComps" /></a></li>
					<li><a href="complaint/referee/myAssignedComplaints.do"><spring:message code="master.page.referee.myComps" /></a></li>
					<li><a href="report/referee/listAll.do"><spring:message code="master.page.referee.myReports" /></a></li>		
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('SPONSOR')">
			<li><a class="fNiv"><spring:message	code="master.page.sponsor.sponsorships" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="sponsorship/new.do"><spring:message code="master.page.sponsor.newSponsorship" /></a></li>
					<li><a href="sponsorship/mySponsorships.do"><spring:message code="master.page.sponsor.mySponsorships" /></a></li>		
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
			<li>
				<a class="fNiv" href="box/myBoxes.do"><spring:message code="master.page.messages" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="box/myBoxes.do"><spring:message code="master.page.messages.myBoxes" /></a></li>
					<li><a href="message/new.do"><spring:message code="master.page.messages.new" /></a></li>
					<security:authorize access="hasAnyRole('HANDYWORKER', 'CUSTOMER')">
						<li><a href="endorsement/handyworker,customer/receivedEndorsements.do"><spring:message code="master.page.handyCus.myEndorsements" /></a></li>
					</security:authorize>
					<security:authorize access="hasRole('HANDYWORKER')">
						<li><a href="complaint/handyworker/myComplaints.do"><spring:message code="master.page.handy.myComplaints" /></a></li>
					</security:authorize>
				</ul>
			</li>
			<li>
				<a class="fNiv" href="profile/see.do"><spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)</a>
			    <ul>
					<li class="arrow"></li>
					<li><a href="profile/edit.do"><spring:message code="master.page.profile.edit" /></a></li>
				</ul>
			</li>
			<li><a class="fNiv" href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
		</security:authorize>
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
			<li><a class="fNiv" href="security/register.do"><spring:message code="master.page.register" /></a></li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

