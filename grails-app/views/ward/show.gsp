<%@ page import="zingit.patientfeedback.QuestionType; zingit.patientfeedback.Ward" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'ward.label', default: 'Ward')}"/>
    <title>${wardInstance.healthService} ${wardInstance.name}</title>
</head>

<body>
<a href="#show-ward" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                           default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="create" controller="feedback" action="create">Feedback</g:link></li>
        <li><g:link class="list" controller="healthService" action="list">Health Services</g:link></li>
        <li><g:link class="list" controller="clinicalServiceDepartment" action="list">Clinical Service Departments</g:link></li>
        <sec:ifAnyGranted roles="ROLE_ADMIN">
            <li><g:link class="list" action="list"><g:message code="default.list.label"
                                                              args="[entityName]"/></g:link></li>
            <li><g:link class="create" action="create"><g:message code="default.new.label"
                                                                  args="[entityName]"/></g:link></li>
        </sec:ifAnyGranted>
    </ul>
</div>

<div id="show-ward" class="content scaffold-show" role="main">
    <h1>${wardInstance.healthService} - ${wardInstance.name}</h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>

    <div class="custom-body">
        <g:set var="questions" value="${wardInstance.healthService.type.questions.findAll({it.type == QuestionType.RATING})}" />
        <g:render template="/common/chart" model="${[args: [id: wardInstance.id], questions: questions]}"/>

        <table class="ratings" style="float: left">
            <tr>
                <th>Question</th>
                <th>Rating</th>
                <th>Responses</th>
            </tr>
            <tr class="odd">
                <g:set var="rating" value="${wardInstance.getNpsRating()}"/>
                <td class="question">NPS Rating</td>
                <td class="score"><pf:stars value="${rating.rating}" count="${rating.count}"/></td>
                <td>${rating.count}</td>
            </tr>
            <g:each in="${wardInstance.healthService.type.questions.findAll({it.type == QuestionType.RATING})}" var="question" status="i">
                <tr class="${i % 2 == 1 ? 'odd' : 'even'}">
                    <g:set var="rating" value="${wardInstance.getRatingForQuestion(question)}" />
                    <td class="question">${question.shortTitle}</td>
                    <td class="score"><pf:stars value="${rating.rating}" count="${rating.count}"/></td>
                    <td>${rating.count}</td>
            </tr>
            </g:each>
            <tr class="total">
                <g:set var="overallRating" value="${wardInstance.getRating()}" />
                <td class="question">Overall</td>
                <td class="score"><pf:stars value="${overallRating.rating}" count="${overallRating.count}" colour="red"/></td>
                <td>${overallRating.count}</td>
            </tr>
        </table>

        <g:render template="/common/comments" model="${[feedbacks: feedbacks, totalCount: feedbacks.totalCount, id: wardInstance.id]}" />
    </div>

    <sec:ifAnyGranted roles="ROLE_ADMIN">
        <g:form>
            <fieldset class="buttons">
                <g:hiddenField name="id" value="${wardInstance?.id}"/>
                <g:link class="edit" action="edit" id="${wardInstance?.id}"><g:message code="default.button.edit.label"
                                                                                       default="Edit"/></g:link>
                <g:actionSubmit class="delete" action="delete"
                                value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                                onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
            </fieldset>
        </g:form>
    </sec:ifAnyGranted>
</div>
</body>
</html>
