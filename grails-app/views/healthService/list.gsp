<%@ page import="zingit.patientfeedback.HealthService" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'healthService.label', default: 'HealthService')}"/>
    <title>Health Services</title>
    <g:javascript>
        $(document).ready(function() {
            $('#hopitals').change(function() {
                window.location = "${createLink(controller: 'healthService', action: 'show')}/" + $(this).val();
            });

            $("#healthServiceDisplay").autocomplete({
                source: '${createLink(controller: 'feedback', action: 'ajaxGetHealthServices')}',
                minLength: 2,
                select: function( event, ui ) {
                    window.location = "${createLink(controller: 'healthService', action: 'show')}/" + ui.item.id;
                }
            });
        });
    </g:javascript>
</head>

<body>
<a href="#list-healthService" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>

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

<div id="select-healthService">
    Choose Health Service <input id="healthServiceDisplay" required="" style="width: 300px" />
</div>

<div id="list-healthService" class="content scaffold-list" role="main">
    <h1>Health Services <span class="count">(${healthServiceInstanceTotal} total)</span></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <table>
        <thead>
        <tr>

            <g:sortableColumn property="name" title="${message(code: 'healthService.name.label', default: 'Name')}"/>
            <th>Type</th>
            <th>Rating</th>
            <th>Responses</th>

        </tr>
        </thead>
        <tbody>
        <g:each in="${healthServiceInstanceList}" status="i" var="healthServiceInstance">
            <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                <g:set var="rating" value="${healthServiceInstance.rating}" />
                <td><g:link action="show" id="${healthServiceInstance.id}">${fieldValue(bean: healthServiceInstance, field: "name")}</g:link></td>
                <td>${healthServiceInstance.type}</td>
                <td><pf:stars value="${rating.rating}" count="${rating.count}" colour="red" /></td>
                <td>${rating.count}</td>

            </tr>
        </g:each>
        </tbody>
    </table>

    <div class="pagination">
        <g:paginate total="${healthServiceInstanceTotal}"/>
    </div>
</div>
</body>
</html>
