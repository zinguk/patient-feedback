<%@ page import="zingit.patientfeedback.Ward" %>



<div class="fieldcontain ${hasErrors(bean: wardInstance, field: 'name', 'error')} required">
    <label for="name">
        <g:message code="ward.name.label" default="Name"/>
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="name" maxlength="80" required="" value="${wardInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: wardInstance, field: 'healthService', 'error')} required">
    <label for="healthService">
        <g:message code="ward.healthService.label" default="Hospital"/>
        <span class="required-indicator">*</span>
    </label>
    <g:select id="healthService" name="healthService.id" from="${zingit.patientfeedback.HealthService.list()}" optionKey="id"
              required="" value="${wardInstance?.healthService?.id}" class="many-to-one"/>
</div>

