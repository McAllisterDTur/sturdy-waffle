<%--
 * action-2.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.4.0/Chart.min.js"></script>


<canvas id="myChart" class="chartjs" width="770" height="385" style="display: block; width: 770px; height: 385px;"></canvas>
<p><spring:message code="administrator.action.2" /></p>
<script type="text/javascript">
var ctx = document.getElementById('myChart').getContext('2d');
var all = "${statistics.get('count.all.shouts')}";
var shr = "${statistics.get('count.short.shouts')}";
var lng = "${statistics.get('count.long.shouts')}";
var chart = new Chart(ctx, {
    // The type of chart we want to create
    type: 'bar',

    // The data for our dataset
    data: {
        labels: ["<spring:message code="administrator.count.all.shouts" />", "<spring:message code="administrator.count.short.shouts" />", "<spring:message code="administrator.count.long.shouts" />"],
        datasets: [{
            label: "<spring:message code="administrator.indicator" />",
            backgroundColor: 'rgb(255, 99, 132)',
            borderColor: 'rgb(255, 99, 132)',
            data: [all, shr,lng]
        }]
    },

    // Configuration options go here
    options: {}
});
</script>
