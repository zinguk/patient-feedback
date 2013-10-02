<%@ page import="zingit.patientfeedback.Relationship; javax.management.relation.Relation; zingit.patientfeedback.HealthService; zingit.patientfeedback.Ward; zingit.patientfeedback.Feedback" %>

<g:javascript>
    $(function() {
        function updateWards() {
            var healthServiceId = $('#healthServiceId').val();
            if (healthServiceId == '') {
                $(".ward").hide();
            } else {
                jQuery.ajax({
                    type:'POST',
                    url:'/feedback/ajaxGetWardsForHealthService/' + healthServiceId ,
                    success:function(data, textStatus) {
                        updateSelect(data, $('#ward'), showOrHide($(".ward"), data.length > 1));
                    },
                    error:function(XMLHttpRequest,textStatus,errorThrown){
                        alert('An error has occurred');
                    }
                });
            }
        }

        function updateClinicalServiceDepartments() {
            var healthServiceId = $('#healthServiceId').val();
            if (healthServiceId == '') {
                $(".clinicalServiceDepartment").hide();
            } else {
                jQuery.ajax({
                    type:'POST',
                    url:'/feedback/ajaxGetCSDsForHealthService/' + healthServiceId ,
                    success:function(data, textStatus) {
                        updateSelect(data, $('#clinicalServiceDepartment'), showOrHide($(".clinicalServiceDepartment"), data.length > 0));
                    },
                    error:function(XMLHttpRequest,textStatus,errorThrown){
                        alert('An error has occurred');
                    }
                });
            }
            updateClinicians();
        }

        function updateClinicians() {
            var healthServiceId = $('#healthServiceId').val();
            var clinicalServiceDepartmentId = $('#clinicalServiceDepartment').val();
            if (healthServiceId == '' || clinicalServiceDepartmentId == null) {
                $(".clinician").hide();
            } else {
                jQuery.ajax({
                    type:'POST',
                    url:'/feedback/ajaxGetClinicians?healthService.id=' + healthServiceId + '&clinicalServiceDepartment.id=' + clinicalServiceDepartmentId,
                    success:function(data, textStatus) {
                        updateSelect(data, $('#clinician'), showOrHide($(".clinician"), data.length > 1));
                    },
                    error:function(XMLHttpRequest,textStatus,errorThrown){
                        alert('An error has occurred');
                    }
                });
            }
        }

        <%-- function updateHealthServices() {
            var healthServiceTypeId = $('#healthServiceType').val();
            jQuery.ajax({
                type:'POST',
                url:'/feedback/ajaxGetHealthServicesForType/' + healthServiceTypeId ,
                success:function(data, textStatus) {
                    updateSelect(data, $('#healthService'));
                    updateWards();
                    updateClinicalServiceDepartments();
                },
                error:function(XMLHttpRequest,textStatus,errorThrown){
                    alert('An error has occurred');
                }
            });
        }--%>

		$("#healthServiceDisplay").autocomplete({
			source: function(request, response) {
			    $.ajax({
                    url: '${createLink(controller: 'feedback', action: 'ajaxGetHealthServices')}',
                    datatype: "json",
                    data: {
                        term: request.term,
                        'healthServiceType.id': $('#healthServiceType').val()
                    },
					success: function( data, status ) {
							response( data );
					}
                });
			},
			minLength: 2,
			select: function( event, ui ) {
			    $('#healthServiceId').val(ui.item.id);
				updateWards();
                updateClinicalServiceDepartments();
			}
        });

        $('#healthServiceType').change(function() {
            $('#healthServiceId').val('');
            $('#healthServiceDisplay').val('');
            updateWards();
            updateClinicalServiceDepartments();
        });

        $('#clinicalServiceDepartment').change(function() {
            updateClinicians();
        });

        updateWards();
        updateClinicalServiceDepartments();
    });
</g:javascript>

<div class="fieldcontain required">
    <label for="healthServiceType">
        <g:message code="feedback.healthServiceType.label" default="Health Service Type"/>
        <span class="required-indicator">*</span>
    </label>
    <g:select id="healthServiceType" name="healthServiceType.id" from="${zingit.patientfeedback.HealthServiceType.list()}" optionKey="id" required="" value="${feedbackInstance?.healthService?.type?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: feedbackInstance, field: 'healthService', 'error')} required">
    <label for="healthService">
        <g:message code="feedback.healthService.label" default="Health service name or post code"/>
        <span class="required-indicator">*</span>
    </label>

    <input type="hidden" id="healthServiceId" name="healthService.id" value="${feedbackInstance?.healthService?.id}" style="width: 40px">
    <input id="healthServiceDisplay" required="" value="${feedbackInstance?.healthService}" style="width: 400px" />
    <%--
    <g:select id="healthService" name="healthService.id" from="${[]}" optionKey="id" required="" value="${feedbackInstance?.healthService?.id}" class="many-to-one"/>

    --%>

</div>

<div class="clinicalServiceDepartment fieldcontain ${hasErrors(bean: feedbackInstance, field: 'clinicalServiceDepartment', 'error')} required">
    <label for="clinicalServiceDepartment">
        <g:message code="feedback.clinicalServiceDepartment.label" default="Which dept have you been in?"/>
        <span class="required-indicator">*</span>
    </label>
    <g:select id="clinicalServiceDepartment" name="clinicalServiceDepartment.id" from="${zingit.patientfeedback.ClinicalServiceDepartment.list()}" optionKey="id" required="" value="${feedbackInstance?.clinicalServiceDepartment?.id}" class="many-to-one"/>
</div>


<div class="clinician fieldcontain ${hasErrors(bean: feedbackInstance, field: 'clinician', 'error')} required">
    <label for="clinician">
        <g:message code="feedback.clinician.label" default="Which clinician did you see?"/>
    </label>
    <select id="clinician" name="clinician.id" value="${feedbackInstance?.clinician?.id}"></select>
</div>

<div class="ward fieldcontain ${hasErrors(bean: feedbackInstance, field: 'ward', 'error')} required" style="${feedbackInstance?.ward ? '' : 'display: none'}">
    <label for="ward">
        <g:message code="feedback.ward.label" default="Ward"/>
    </label>
    <g:select id="ward" name="ward.id" from="${[]}" optionKey="id" value="${feedbackInstance?.ward?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: feedbackInstance, field: 'anonymous', 'error')} ">
    <label for="anonymous">
        <g:message code="feedback.anonymous.label" default="Please keep my (the patient's) name confidential"/>

    </label>
    <g:checkBox name="anonymous" value="${feedbackInstance?.anonymous}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: feedbackInstance, field: 'relationship', 'error')} required">
    <label for="relationship">
        <g:message code="feedback.relationship.label" default="Relationship to Patient"/>
        <span class="required-indicator">*</span>
    </label>
    <g:select id="relationship" name="relationship" from="${Relationship.values()}" optionValue="value" required="" value="${feedbackInstance?.relationship}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: feedbackInstance, field: 'firstName', 'error')} required">
    <label for="firstName">
        <g:message code="feedback.firstName.label" default="First Name"/>
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="firstName" maxlength="20" required="" value="${feedbackInstance?.firstName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: feedbackInstance, field: 'surname', 'error')} required">
    <label for="surname">
        <g:message code="feedback.surname.label" default="Surname"/>
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="surname" maxlength="30" required="" value="${feedbackInstance?.surname}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: feedbackInstance, field: 'postCode', 'error')} required">
    <label for="postCode">
        <g:message code="feedback.postCode.label" default="Post Code"/>
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="postCode" maxlength="10" required="" value="${feedbackInstance?.postCode}"/>
</div>

<%-- <div class="fieldcontain ${hasErrors(bean: feedbackInstance, field: 'nhsNumber', 'error')} required">
	<label for="nhsNumber">
		<g:message code="feedback.nhsNumber.label" default="NHS Number" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nhsNumber" maxlength="12" required="" value="${feedbackInstance?.nhsNumber}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: feedbackInstance, field: 'patientNumber', 'error')} ">
	<label for="patientNumber">
		<g:message code="feedback.patientNumber.label" default="Patient Number" />

	</label>
	<g:textField name="patientNumber" maxlength="20" value="${feedbackInstance?.patientNumber}"/>
</div> --%>

<div class="fieldcontain ${hasErrors(bean: feedbackInstance, field: 'dateOfVisit', 'error')} required">
    <label for="dateOfVisit">
        <g:message code="feedback.dateOfVisit.label" default="Date Of Visit"/>
        <span class="required-indicator">*</span>
    </label>
    <g:datePicker name="dateOfVisit" precision="day" years="[2011, 2012, 2013, 2014, 2015, 2016, 2017, 2018, 2019, 2020]" value="${feedbackInstance?.dateOfVisit}"/>
</div>

<div class="fieldcontain contact-detail">If you are happy to be contacted regarding this feedback please provide your:</div>

<div class="fieldcontain contact-detail ${hasErrors(bean: feedbackInstance, field: 'emailAddress', 'error')} ">
    <label for="emailAddress">
        <g:message code="feedback.emailAddress.label" default="Email Address"/>

    </label>
    <g:field type="email" name="emailAddress" value="${feedbackInstance?.emailAddress}"/>
</div>

<div class="fieldcontain contact-detail ${hasErrors(bean: feedbackInstance, field: 'phoneNumber', 'error')} ">
    <label for="phoneNumber">
        <g:message code="feedback.phoneNumber.label" default="Phone Number"/>

    </label>
    <g:textField name="phoneNumber" maxlength="15" value="${feedbackInstance?.phoneNumber}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: feedbackInstance, field: 'npsRating', 'error')} required">
    <label for="npsRating" style="float: left">
        <g:message code="feedback.npsRating.label" default="Would you recommend this Health Service to a friend?"/>
        <span class="required-indicator">*</span>
    </label>

    <div class="ratingValues">
        <span class="left">Poor</span><span class="right">Excellent</span>
        <ul>
            <g:radioGroup name="npsRating" values="${(1..10)}" labels="${(1..10)}" value="${feedbackInstance.npsRating}"><li>${it.radio} ${it.label}</li></g:radioGroup>
        </ul>
    </div>

    <div style="clear: both"></div>
</div>

<div class="fieldcontain ${hasErrors(bean: feedbackInstance, field: 'npsReason', 'error')} ">
    <label for="npsReason">
        Please provide optional explanation to your answer score above (1000 character limit)
    </label>
    <g:textArea name="npsReason" cols="40" rows="5" maxlength="1000" value="${feedbackInstance.npsReason}"/>
</div>