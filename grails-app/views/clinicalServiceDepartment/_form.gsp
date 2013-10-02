<%@ page import="zingit.patientfeedback.ClinicalServiceDepartment" %>



<div class="fieldcontain ${hasErrors(bean: clinicalServiceDepartmentInstance, field: 'name', 'error')} required">
    <label for="name">
        <g:message code="clinicalServiceDepartment.name.label" default="Name"/>
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="name" maxlength="80" required="" value="${clinicalServiceDepartmentInstance?.name}"/>
</div>

