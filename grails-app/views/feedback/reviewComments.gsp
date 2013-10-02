<%@ page import="zingit.patientfeedback.FeedbackVisibility; zingit.patientfeedback.Feedback" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'feedback.label', default: 'Feedback')}"/>
    <title>Review Comments</title>
    <g:javascript>
        $(function() {
            $('#visibility').change(function(eventObject) {
                document.commentForm.submit();
            });
        });
    </g:javascript>
</head>

<body>
<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
    </ul>
</div>

<div class="content scaffold-create" role="main">
    <h1>Review Comments</h1>
    <g:form method="post" name="commentForm" action="reviewComments">
        <div style="margin: 10px 21px">Show Comments <g:select from="${FeedbackVisibility.values()}" optionValue="value" name="visibility" value="${params.visibility}"/></div>
    </g:form>
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>HealthService</th>
            <th>Feedback</th>
            <th>Action</th>

        </tr>
        </thead>
        <tbody>
        <g:each in="${feedbacks}" status="i" var="feedback">
            <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                <td>${feedback.id}</td>
                <td>${feedback.healthService}</td>
                <td>${feedback.npsReason}</td>
                <td><g:link action="showComment" id="${feedback.id}" params="${[visibility: params.visibility]}">show</g:link>/<g:link action="hideComment" id="${feedback.id}" params="${[visibility: params.visibility]}">hide</g:link></td>

            </tr>
        </g:each>
        </tbody>
    </table>

    <div class="pagination">
        <g:paginate total="${totalCount}"/>
    </div>
</div>
</body>
</html>
