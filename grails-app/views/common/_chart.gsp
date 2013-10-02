<%@ page import="zingit.patientfeedback.util.DateUtil; zingit.patientfeedback.Question" %>
<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<script type="text/javascript">
    google.load("visualization", "1", {packages:["corechart"]});
    google.setOnLoadCallback(drawChart);
    function drawChart() {
        var data = new google.visualization.DataTable();
        data.addColumn('string', 'Month');
        data.addColumn('number', 'Rating');
        <g:if test="${nationalMonthlyRatings}">
            data.addColumn('number', 'National Average (relating to feedback provided through this site on all health services)');
        </g:if>
        data.addRows([
            <g:each in="${(11..0)}" var="monthsAgo">
                <g:set var="date" value="${DateUtil.monthsAgo(monthsAgo)}" />
                <g:set var="year" value="${date.year + 1900}" />
                <g:set var="month" value="${date.month + 1}" />
                <g:set var="rating" value="${monthlyRatings.find({it.year == year && it.month == month})}" />
                <g:set var="nationalRating" value="${nationalMonthlyRatings.find({it.year == year && it.month == month})}" />
                ['<g:formatDate date="${date}" format="MMM yy" />',${rating?.overall ?: 'null'}<g:if test="${nationalMonthlyRatings}">,${nationalRating?.overall ?: 'null'}</g:if>]<g:if test="${monthsAgo > 0}">,</g:if>
            </g:each>
        ]);

        var options = {
            width: 920, height: 240, interpolateNulls: true, vAxis: {minValue: 0, maxValue: 10}, legend: {position: 'bottom'}
        };

        var chart = new google.visualization.LineChart(document.getElementById('chart_div'));
        chart.draw(data, options);
    }

    $(function() {
        $("#question\\.id").change(function() {
            document.chartForm.submit();
        })
    });
</script>

<h2 style="padding-left: 0.2em">${params['question.id'] ? Question.get(params.long('question.id')).shortTitle : 'Overall score'} by period</h2>

<div style="padding-left: 5px">
    <g:form name="chartForm" action="${actionName}" params="${args}">
        View ratings for:
        <g:select name="question.id" optionKey="id" optionValue="shortTitle" value="${params['question.id']}" from="${questions}" noSelection="['':'All Questions']"/>
    </g:form>
</div>

<div id='chart_div' style='width: 700px; height: 240px; margin-bottom: 25px;'></div>