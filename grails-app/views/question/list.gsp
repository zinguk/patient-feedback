<%@ page import="zingit.patientfeedback.Question" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'question.label', default: 'Question')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
    <g:javascript>
        $(function () {
            $('#healthServiceType\\.id').change(function () {
                document.healthServiceForm.submit();
            });

            $("#questions tbody").sortable({
                delay: 50,
                items: 'tr',
                tolerance: 'pointer',
                update: function(event, ui) {
                    jQuery.ajax({type:'GET', url:'${resource(uri: '/')}/question/updateRank/' + ui.item.data("id"), data: {after: ui.item.prev().data("id"), before: ui.item.next().data("id")}, success:function(data,textStatus){},error:function(XMLHttpRequest,textStatus,errorThrown){handleError(XMLHttpRequest);}});
                }
            }).disableSelection();
        });
    </g:javascript>
    <style>
        #questions tbody tr {
            cursor: move;
        }
    </style>
</head>

<body>
<a href="#list-question" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]"/></g:link></li>
    </ul>
</div>

<div id="list-question" class="content scaffold-list" role="main">
    <h1 style="float: left"><g:message code="default.list.label" args="[entityName]"/></h1>

    <div style="float: right; padding: 12px 24px;">
        <g:form action="list" name="healthServiceForm">
            Health Service Type: <g:select name="healthServiceType.id" from="${healthServiceTypes}" optionKey="id" value="${filter.healthServiceType.id}"/>
        </g:form>
    </div>

    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <table id="questions">
        <thead>
        <tr>

            <th><g:message code="question.healthServiceType.label" default="Health Service Type"/></th>

            <g:sortableColumn property="shortTitle" title="${message(code: 'question.shortTitle.label', default: 'Short Title')}"/>

            <g:sortableColumn property="fullQuestion" title="${message(code: 'question.fullQuestion.label', default: 'Full Question')}"/>

            <g:sortableColumn property="type" title="${message(code: 'question.type.label', default: 'Type')}"/>

        </tr>
        </thead>
        <tbody>
        <g:each in="${questionInstanceList}" status="i" var="questionInstance">
            <tr class="${(i % 2) == 0 ? 'even' : 'odd'}" data-id="${questionInstance.id}">

                <td><g:link action="show" id="${questionInstance.id}">${fieldValue(bean: questionInstance, field: "healthServiceType")}</g:link></td>

                <td>${fieldValue(bean: questionInstance, field: "shortTitle")}</td>

                <td>${fieldValue(bean: questionInstance, field: "fullQuestion")}</td>

                <td>${fieldValue(bean: questionInstance, field: "type")}</td>

            </tr>
        </g:each>
        </tbody>
    </table>
</div>
</body>
</html>
