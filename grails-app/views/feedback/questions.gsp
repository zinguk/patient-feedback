<%@ page import="zingit.patientfeedback.QuestionType; zingit.patientfeedback.Feedback" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'feedback.label', default: 'Feedback')}"/>
    <title>Questions</title>
    <g:javascript library="jquery.textarea-maxlength"/>
    <g:javascript>
        $(document).ready(function () {
            var onEditCallback = function (remaining) {
                $(this).siblings('.charsRemaining').text("Characters remaining: " + remaining);

                if (remaining > 0) {
                    $(this).css('background-color', 'white');
                }
            }

            var onLimitCallback = function () {
                $(this).css('background-color', 'red');
            }

            $('textarea[maxlength]').limitMaxlength({
                onEdit:onEditCallback,
                onLimit:onLimitCallback
            });
        });
    </g:javascript>
</head>

<body>
<a href="#create-feedback" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="create" controller="feedback" action="create">Feedback</g:link></li>
        <li><g:link class="list" controller="healthService" action="list">Health Services</g:link></li>
        <li><g:link class="list" controller="clinicalServiceDepartment" action="list">Clinical Service Departments</g:link></li>
    </ul>
</div>

<div id="create-feedback" class="content scaffold-create" role="main">
    <h1>Thank You</h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${cmd}">
        <ul class="errors" role="alert">
            <g:eachError bean="${cmd}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
            </g:eachError>
        </ul>
    </g:hasErrors>

    <g:form action="saveQuestions">
        <g:hiddenField name="feedbackInstance.id" value="${cmd.feedbackInstance.id}" />
        <fieldset class="form">

            Your feedback has been recorded. We would appreciate it if you could take some more time to complete the additional questions below.

            <g:each in="${cmd.feedbackInstance.healthService.type.questions}" var="question" status="i">
                <g:if test="${question.type == QuestionType.RATING}">
                    <div class="fieldcontain ${hasErrors(bean: cmd, field: "answers[${i}]", 'error')} required">
                        <label for="answers[${i}]" style="float: left">
                            ${question.fullQuestion}
                            <span class="required-indicator">*</span>
                        </label>

                        <div class="ratingValues">
                            <span class="left">Poor</span><span class="right">Excellent</span>
                            <ul>
                                <g:radioGroup name="answers[${i}]" values="${(1..10)}" labels="${(1..10)}" value="${cmd.answers[i]}"><li>${it.radio} ${it.label}</li></g:radioGroup>
                            </ul>
                        </div>

                        <div style="clear: both"></div>
                    </div>
                </g:if>
                <g:else>
                    <div class="fieldcontain ${hasErrors(bean: cmd, field: "answers[${i}]", 'error')} ">
                        <label for="answers[${i}]">
                            ${question.fullQuestion}
                        </label>
                        <g:textArea name="answers[${i}]" cols="40" rows="5" maxlength="1000" value="${cmd.answers[i]}"/>
                    </div>
                </g:else>
            </g:each>
        </fieldset>
        <fieldset class="buttons">
            <g:submitButton  name="create" class="save" value="Submit Feedback"/>
        </fieldset>
    </g:form>
</div>
</body>
</html>
