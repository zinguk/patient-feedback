<%@ page import="zingit.patientfeedback.Feedback; zingit.patientfeedback.ClinicalServiceDepartment" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'clinicalServiceDepartment.label', default: 'ClinicalServiceDepartment')}"/>
    <title>Clinical Service Departments</title>
</head>

<body>
<a href="#list-clinicalServiceDepartment" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="create" controller="feedback" action="create">Feedback</g:link></li>
        <li><g:link class="list" controller="healthService" action="list">Health Services</g:link></li>
        <li><g:link class="list" controller="clinicalServiceDepartment" action="list">Clinical Service Departments</g:link></li>
        <sec:ifAnyGranted roles="ROLE_ADMIN">
            <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]"/></g:link></li>
        </sec:ifAnyGranted>
    </ul>
</div>

<div id="list-clinicalServiceDepartment" class="content scaffold-list" role="main">
    <h1>Clinical Service Departments <span class="count">(${clinicalServiceDepartmentInstanceTotal} total)</span></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <table>
        <thead>
        <tr>

            <g:sortableColumn property="name" title="${message(code: 'clinicalServiceDepartment.name.label', default: 'Name')}"/>
            <th>Hospitals Rated</th>

        </tr>
        </thead>
        <tbody>
        <g:each in="${clinicalServiceDepartmentInstanceList}" status="i" var="clinicalServiceDepartmentInstance">
            <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

                <td><g:link action="show" id="${clinicalServiceDepartmentInstance.id}">${fieldValue(bean: clinicalServiceDepartmentInstance, field: "name")}</g:link></td>
                <td>${Feedback.countByClinicalServiceDepartment(clinicalServiceDepartmentInstance)}</td>

            </tr>
        </g:each>
        </tbody>
    </table>

    <div class="pagination">
        <g:paginate total="${clinicalServiceDepartmentInstanceTotal}"/>
    </div>
</div>
</body>
</html>
