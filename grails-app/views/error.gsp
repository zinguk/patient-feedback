<%@ page import="zingit.patientfeedback.Feedback" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'errors.css')}" type="text/css">
    <title>Oops, something went wrong...</title>
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

<div class="content" role="main">
    <g:if env="development">
        <g:renderException exception="${exception}" />
    </g:if>
    <g:else>
        <h1>Oops, something went wrong...</h1>
        <div style="margin-left: 22px">
            We're sorry, something has gone wrong. Please try again.
        </div>
    </g:else>
</div>
</body>
</html>
