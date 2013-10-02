<%@ page import="zingit.patientfeedback.Feedback" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'feedback.label', default: 'Feedback')}"/>
    <title>Feedback Left</title>
</head>

<body>
<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="create" controller="feedback" action="create">Feedback</g:link></li>
        <li><g:link class="list" controller="healthService" action="list">Health Services</g:link></li>
        <li><g:link class="list" controller="clinicalServiceDepartment" action="list">Clinical Service Departments</g:link></li>
    </ul>
</div>

<div class="content scaffold-create" role="main">
    <h1>Your Feedback has been recorded</h1>
    <div style="padding-left: 25px;">
        Thank you very much.
    </div>
</div>
</body>
</html>
