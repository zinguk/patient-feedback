<%@ page import="zingit.patientfeedback.HealthService" %>



<div class="fieldcontain ${hasErrors(bean: healthServiceInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="healthService.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" maxlength="80" required="" value="${healthServiceInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: healthServiceInstance, field: 'type', 'error')} required">
	<label for="type">
		<g:message code="healthService.type.label" default="Type" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="type" name="type.id" from="${zingit.patientfeedback.HealthServiceType.list()}" optionKey="id" required="" value="${healthServiceInstance?.type?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: healthServiceInstance, field: 'postCode', 'error')} required">
	<label for="postCode">
		<g:message code="healthService.postCode.label" default="Post Code" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="postCode" maxlength="10" required="" value="${healthServiceInstance?.postCode}"/>
</div>