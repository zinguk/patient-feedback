<%@ page import="zingit.patientfeedback.Feedback" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Patient Feedback</title>
    <style type="text/css">
    .content p {
        padding-left: 1.5em;
        padding-right: 1.5em;
        padding-top: 0.7em;
    }

    .content ul {
        list-style: decimal;
        padding-left: 3em;
        padding-right: 1.5em;
        padding-top: 0.7em;
    }

    .content li {
        padding-bottom: 0.4em;
    }
    </style>
</head>

<body>
<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="create" controller="feedback" action="create">Feedback</g:link></li>
        <li><g:link class="list" controller="healthService" action="list">Health Services</g:link></li>
        <li><g:link class="list" controller="clinicalServiceDepartment" action="list">Clinical Service Departments</g:link></li>
    </ul>
</div>

<div id="create-feedback" class="content scaffold-create" role="main">
    <h1>Patient Feedback</h1>

    <p><b>Reason for developing this website demonstration.</b></p>

    <p>This site provides an easy and cost effective way for users of UK hospitals, Doctor’s Surgeries (and potentially Care Homes) to provide timely regular feedback on non-clinical service levels.</p>

    <p>With the benefit of regularly captured, high volume sample sizes of feedback on standardised questions, everyone from patients, their relatives and friends, hospital staff, management, trustees, regulators, the government and taxpayers can better understand users' input on how well individual health service providers are performing over time.</p>

    <p>Users of the site can also easily compare, for example a hospital’s performance against the UK average hospital performance and easily see improvements or deterioration trends relating to all, for example doctor’s surgeries or an individual doctor’s surgery over time. For hospitals, feedback and comparisons can be made by Clinical Service Departments as well as for an ‘overall’ Hospital.</p>

    <p>To provide greater confidence in the integrity of the feedback, reduce the likelihood of a skew towards negative feedback only (as displeased service users usually feedback more often than pleased service users and avoid the risk that some people do not feedback negatively because they are concerned about their privacy, we recommend the following:</p>

    <ul>
        <li>Critically the site should be operated by an independent charity and feedbackers should be able to provide feedback anonymously meaning their names are not passed on to any of the hospitals.</li>

        <li>One can filter for reports where feedback has been given only by a patient or their relative or friend who we’ve been able to verify (name/postcode match to eg 192. (Whilst this may cost 60p per person the first time they feedback, it increases the credibility of the feedback.</li>

        <li>Possibly only report on the first feedback for a hospital and the first feedback for a doctor’s surgery provided in the calendar month by a patient – to reduce the risk of multiple spam feedback – although name/postcode verification reduces the risk of reporting spam feedback from ‘made up people at wrong post codes.</li>
    </ul>

    <p>Feedback comments (entered as “free text”) will only be published anonymously and only where there is no inappropriate language and no inclusion of named people whether hospital staff, the patient, or relative or friend providing feedback).</p>

    <p>Feedback can be submitted online and some hospitals may provide tablets for inpatients or terminals for outpatients to ensure as healthy a sample of feedback is captured as possible on a timely basis.</p>

    <p>It is simple – and cost effective - for this Feedback site to allow individual hospitals and doctor’s surgeries to ask:</p>

    <ul>
        <li>The 2 Net Promotor Score questions only – (1) Would you recommend this service to a friend or relative. (1-10 answer) and (2) Why (Open text field answer).</li>

        <li>A short number of further (nationally) standardised questions relevant to a: (1) Hospital Inpatient, (2) Hospital Outpatient or (3) Doctor’s Surgery or (4) Care Home</li>

        <li>Further questions that an individual hospital, clinical service department within a hospital, doctor’s surgery or care home may want to ask from time to time – and view the results of privately.</li>
    </ul>

    <p>Obviously to fulfill 3, individual hospitals or doctor’s surgeries will need secure log in ability to create their own questions and answer formats.</p>

    <p>To exploit Net Promotor Scores fully, the government, NHS or individual hospitals will need to decide whether they want, like Broomfield Hospital, to provide details of indvidiual clinicians or doctors’ to monitor feedback by clinician / doctor.</p>

</div>
</body>
</html>
