<%@ page import="zingit.patientfeedback.QuestionType; zingit.patientfeedback.util.DateUtil; java.math.RoundingMode; zingit.patientfeedback.HealthService" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'healthService.label', default: 'HealthService')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>
<a href="#show-healthService" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                                    default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="create" controller="feedback" action="create">Feedback</g:link></li>
        <li><g:link class="list" controller="healthService" action="list">Health Services</g:link></li>
        <li><g:link class="list" controller="clinicalServiceDepartment" action="list">Clinical Service Departments</g:link></li>
        <sec:ifAnyGranted roles="ROLE_ADMIN">
            <li><g:link class="create" action="create"><g:message code="default.new.label"
                                                                  args="[entityName]"/></g:link></li>
        </sec:ifAnyGranted>
    </ul>
</div>

<div id="show-healthService" class="content scaffold-show" role="main">
    <h1>${healthServiceInstance}</h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>

    <div class="custom-body">
        <g:set var="questions" value="${healthServiceInstance.type.questions.findAll({it.type == QuestionType.RATING})}" />
        <g:render template="/common/chart" model="${[args: [id: healthServiceInstance.id], questions: questions]}"/>

        <table class="ratings" style="float: left">
            <tr>
                <th>Question</th>
                <th>Rating</th>
                <th>Responses</th>
            </tr>
            <tr class="odd">
                <g:set var="rating" value="${healthServiceInstance.getNpsRating()}"/>
                <td class="question">NPS Rating</td>
                <td class="score"><pf:stars value="${rating.rating}" count="${rating.count}"/></td>
                <td>${rating.count}</td>
            </tr>
            <g:each in="${questions}" var="question" status="i">
                <tr class="${i % 2 == 1 ? 'odd' : 'even'}">
                    <g:set var="rating" value="${healthServiceInstance.getRatingForQuestion(question)}"/>
                    <td class="question">${question.shortTitle}</td>
                    <td class="score"><pf:stars value="${rating.rating}" count="${rating.count}"/></td>
                    <td>${rating.count}</td>
                </tr>
            </g:each>
            <tr class="total">
                <g:set var="overallRating" value="${healthServiceInstance.getRating()}"/>
                <td class="question">Overall</td>
                <td class="score"><pf:stars value="${overallRating.rating}" count="${overallRating.count}" colour="red"/></td>
                <td>${overallRating.count}</td>
            </tr>
        </table>

        <g:if test="${!healthServiceInstance.wards.empty}">
            <table class="ratings" style="float: left">
                <tr>
                    <th>Ward</th>
                    <th>Rating</th>
                    <th>Responses</th>
                </tr>
                <g:each in="${healthServiceInstance.wards}" var="ward" status="i">
                    <tr class="${i % 2 ? 'odd' : 'even'}">
                        <g:set var="wardRating" value="${ward.getRating()}"/>
                        <td class="question"><g:link controller="ward" action="show" id="${ward.id}">${ward.name}</g:link></td>
                        <td class="score"><pf:stars value="${wardRating.rating}" count="${wardRating.count}" colour="red"/></td>
                        <td>${wardRating.count}</td>
                    </tr>
                </g:each>
            </table>
        </g:if>

        <g:if test="${!clinicalServiceDepartments.empty && !clinicalServiceDepartments.contains(null)}">
            <table class="ratings" style="float: left">
                <tr>
                    <th>Clinical Service Department</th>
                    <th>Rating</th>
                    <th>Responses</th>
                </tr>
                <g:each in="${clinicalServiceDepartments}" var="clinicalServiceDepartment" status="i">
                    <tr class="${i % 2 ? 'odd' : 'even'}">
                        <g:set var="csdRating" value="${clinicalServiceDepartment.getRatingForHealthService(healthServiceInstance)}"/>
                        <td class="question"><g:link controller="clinicalServiceDepartment" action="showHospital" id="${clinicalServiceDepartment.id}" params="${[healthServiceId: healthServiceInstance.id]}">${clinicalServiceDepartment.name}</g:link></td>
                        <td class="score"><pf:stars value="${csdRating.rating}" count="${csdRating.count}" colour="red"/></td>
                        <td>${csdRating.count}</td>
                    </tr>
                </g:each>
            </table>
        </g:if>

        <g:render template="/common/comments" model="${[feedbacks: feedbacks, totalCount: feedbacks.totalCount, id: healthServiceInstance.id]}" />
    </div>

    <sec:ifAnyGranted roles="ROLE_ADMIN">
        <g:form>
            <fieldset class="buttons">
                <g:hiddenField name="id" value="${healthServiceInstance?.id}"/>
                <g:link class="edit" action="edit" id="${healthServiceInstance?.id}"><g:message
                        code="default.button.edit.label" default="Edit"/></g:link>
                <g:actionSubmit class="delete" action="delete"
                                value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                                onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
            </fieldset>
        </g:form>
    </sec:ifAnyGranted>
</div>
</body>
</html>
