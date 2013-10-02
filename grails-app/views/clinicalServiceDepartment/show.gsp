<%@ page import="zingit.patientfeedback.HealthService; java.math.RoundingMode; zingit.patientfeedback.ClinicalServiceDepartment" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'clinicalServiceDepartment.label', default: 'ClinicalServiceDepartment')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>
<a href="#show-clinicalServiceDepartment" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="list" action="list">Clinical Service Departments</g:link></li>
        <sec:ifAnyGranted roles="ROLE_ADMIN">
            <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]"/></g:link></li>
        </sec:ifAnyGranted>
    </ul>
</div>

<div id="show-clinicalServiceDepartment" class="content scaffold-show" role="main">
    <h1>${clinicalServiceDepartmentInstance}</h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>

    <table>
        <tr>
            <g:sortableColumn property="name" title="Health Service"/>
            <g:sortableColumn property="rating" title="Rating"/>
            <th>Responses</th>
        </tr>
        <g:each in="${healthServices}" var="healthService" status="i">
            <tr class="${i % 2 ? 'odd' : 'even'}">
                <g:set var="rating" value="${healthService.getRatingForCSD(clinicalServiceDepartmentInstance)}"/>
                <td><g:link controller="clinicalServiceDepartment" action="showHospital" id="${clinicalServiceDepartmentInstance.id}" params="${[healthServiceId: healthService.id]}">${healthService.name}</g:link></td>
                <td><pf:stars value="${rating.rating}" count="${rating.count}" colour="red"/></td>
                <td>${rating.count}</td>
            </tr>
        </g:each>
    </table>

    <sec:ifAnyGranted roles="ROLE_ADMIN">
        <g:form>
            <fieldset class="buttons">
                <g:hiddenField name="id" value="${clinicalServiceDepartmentInstance?.id}"/>
                <g:link class="edit" action="edit" id="${clinicalServiceDepartmentInstance?.id}"><g:message code="default.button.edit.label" default="Edit"/></g:link>
                <g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
            </fieldset>
        </g:form>
    </sec:ifAnyGranted>
</div>
</body>
</html>
