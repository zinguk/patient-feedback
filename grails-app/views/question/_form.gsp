<%@ page import="zingit.patientfeedback.Question" %>



<div class="fieldcontain ${hasErrors(bean: questionInstance, field: 'healthServiceType', 'error')} required">
	<label for="healthServiceType">
		<g:message code="question.healthServiceType.label" default="Health Service Type" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="healthServiceType" name="healthServiceType.id" from="${zingit.patientfeedback.HealthServiceType.list()}" optionKey="id" required="" value="${questionInstance?.healthServiceType?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: questionInstance, field: 'shortTitle', 'error')} required">
	<label for="shortTitle">
		<g:message code="question.shortTitle.label" default="Short Title" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="shortTitle" required="" value="${questionInstance?.shortTitle}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: questionInstance, field: 'fullQuestion', 'error')} required">
	<label for="fullQuestion">
		<g:message code="question.fullQuestion.label" default="Full Question" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="fullQuestion" required="" value="${questionInstance?.fullQuestion}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: questionInstance, field: 'type', 'error')} required">
	<label for="type">
		<g:message code="question.type.label" default="Type" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="type" from="${zingit.patientfeedback.QuestionType?.values()}" keys="${zingit.patientfeedback.QuestionType.values()*.name()}" required="" value="${questionInstance?.type?.name()}"/>
</div>

