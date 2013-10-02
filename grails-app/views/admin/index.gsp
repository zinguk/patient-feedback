<%@ page import="zingit.patientfeedback.Feedback" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Admin Area</title>
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
    <h1>Admin Area</h1>

    <div style="margin-left: 50px">
        <ul>
            <li><g:link controller="feedback" action="reviewComments">Review Feedback</g:link></li>
            <li><g:link controller="healthServiceType" action="list">Manage Health Service Types</g:link></li>
            <li><g:link controller="healthService" action="list">Manage Health Services</g:link></li>
            <li><g:link controller="ward" action="list">Manage Wards</g:link></li>
            <li><g:link controller="clinicalServiceDepartment" action="list">Manage Clinical Service Departments</g:link></li>
            <li><g:link controller="clinician" action="list">Manage Clinicians</g:link></li>
            <li><g:link controller="question" action="list">Manage Questions</g:link></li>
        </ul>
    </div>
</div>
</body>
</html>
