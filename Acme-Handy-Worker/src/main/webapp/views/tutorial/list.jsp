<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('HANDYWORKER')">
	<form action="tutorial/all.do" style="Display: inline">
	  		<input type="submit" value="All tutorials" />
	</form>
	<form action="tutorial/myTutorials.do" style="Display: inline">
	  		<input type="submit" value="My tutorials" />
	</form>
	<form action="tutorial/create.do" style="Display: inline">
	  		<input type="submit" value="New tutorial" />
	</form>
</security:authorize>
<display:table pagesize="5" name="tutorials" id="row" requestURI="tutorial/${test}.do">
	

	<display:column property="title" titleKey="tutorial.title"></display:column>
	<display:column titleKey="tutorial.handyworker"><a href="profile/handyWorker.do?id=${row.worker.id}"><jstl:out value='${row.worker.make}'></jstl:out></a></display:column>
	<display:column property="summary" titleKey="tutorial.summary"></display:column>
	<display:column property="lastTimeUpdated" titleKey="tutorial.lastupdate"></display:column>
	<display:column>
		<form action="tutorial/see.do?tutoId=${row.id}">
	    	<input type="submit" value="See more" />
		</form>
	</display:column>
	
	<security:authorize access="hasRole('HANDYWORKER')">
		<display:column>
			<form action="tutorial/update.do?tutoId=${row.id}">
	    		<input type="submit" value="Update" />
			</form>
		</display:column>	
		<display:column>
			<form action="tutorial/delete.do?tutoId=${row.id}">
	    		<input type="submit" value="Delete" />
			</form>
		</display:column>	
	</security:authorize>
	
	<display:column>
		<form action="tutorial/picture.do?tutoId=${row.id}">
	    	<input type="submit" value="Pictures" />
		</form>
	</display:column>
</display:table>