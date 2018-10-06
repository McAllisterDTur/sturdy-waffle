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
<head>
<script src="http://www.chartjs.org/dist/2.7.2/Chart.bundle.js"></script>
<script src="http://www.chartjs.org/samples/latest/utils.js"></script>
</head>
<body>
<p><spring:message code="administrator.action.2" /></p>

<canvas id="bar-chart" width="800" height="450"></canvas>
<script type="text/javascript">

var all = '${statistics.get("count.all.shouts")}';
var shr = '${statistics.get("count.short.shouts")}';
var lng = '${statistics.get("count.long.shouts")}';

new Chart(document.getElementById("bar-chart"), {
    type: 'bar',
    data: {
      labels: ["<spring:message code='administrator.count.all.shouts' />",
               "<spring:message code='administrator.count.short.shouts' />",
               "<spring:message code='administrator.count.long.shouts' />"],
      datasets: [
        {
          label: "<spring:message code='administrator.graph.label' />",
          backgroundColor: ["#FFE66D", "#FF6B6B","#4ECDC4"],
          data: [all, shr,lng]
        }
      ]
    },
    options: {
      legend: { display: false },
      title: {
        display: true,
        text: "<spring:message code='administrator.graph.title' />"
      },
      scales: {
          yAxes: [{
              ticks: {
                  beginAtZero: true
              }
          }]
      }
    }
});
</script>

</body>



