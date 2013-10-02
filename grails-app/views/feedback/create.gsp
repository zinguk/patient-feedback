<%@ page import="zingit.patientfeedback.Feedback" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'feedback.label', default: 'Feedback')}"/>
    <title>Patient Feedback</title>
    <g:javascript library="jquery.textarea-maxlength"/>
    <g:javascript>
        $(document).ready(function() {
            $('#anonymous').click(function() {
                if ($(this).attr('checked') == undefined) {
                    $('.contact-detail').show();
                } else {
                    $('.contact-detail').hide();
                }
            });
        });
    </g:javascript>
</head>

<body>
<a href="#create-feedback" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                                 default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="create" controller="feedback" action="create">Feedback</g:link></li>
        <li><g:link class="list" controller="healthService" action="list">Health Services</g:link></li>
        <li><g:link class="list" controller="clinicalServiceDepartment" action="list">Clinical Service Departments</g:link></li>
    </ul>
</div>

<div id="create-feedback" class="content scaffold-create" role="main">
    <h1>Leave Feedback</h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${feedbackInstance}">
        <ul class="errors" role="alert">
            <g:eachError bean="${feedbackInstance}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
                        error="${error}"/></li>
            </g:eachError>
        </ul>
    </g:hasErrors>
    <g:form action="save">
        <fieldset class="form">
            <g:render template="form"/>
        </fieldset>
        <fieldset class="buttons">
            <g:submitButton name="create" class="save" value="Submit Feedback"/>
        </fieldset>
    </g:form>
</div>
</body>
</html>
