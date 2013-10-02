package zingit.patientfeedback

import grails.converters.JSON
import grails.plugins.springsecurity.Secured

class FeedbackController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "create", params: params)
    }

    def create() {
        [feedbackInstance: new Feedback(params)]
    }

    def save() {
        def feedbackInstance = new Feedback(params)
        if (!feedbackInstance.save(flush: true)) {
            render(view: "create", model: [feedbackInstance: feedbackInstance])
            return
        }
        
        redirect(action: 'questions', params: ['feedbackInstance.id': feedbackInstance.id])
    }

    def questions(QuestionCommand cmd) {
        if (!cmd || !cmd.feedbackInstance) {
            return redirect(action: 'create')
        }

        if (Answer.countByFeedback(cmd.feedbackInstance) > 0) {
            return redirect(controller: 'home', action: 'index')
        }

        List<Question> questions = cmd.feedbackInstance.healthService.type.questions.findAll({it != null}).sort{it.rank}
        while (cmd.answers.size() < questions.size()) {
            cmd.answers.add(null)
        }

        [cmd: cmd]
    }

    def saveQuestions(QuestionCommand cmd) {
        if (!cmd || !cmd.feedbackInstance) {
            return redirect(action: 'create')
        }

        if (Answer.countByFeedback(cmd.feedbackInstance) > 0) {
            return redirect(controller: 'home', action: 'index')
        }
        
        List<Question> questions = cmd.feedbackInstance.healthService.type.questions
        while (cmd.answers.size() < questions.size()) {
            cmd.answers.add(null)
        }

        cmd.answers.eachWithIndex { it, i ->
            if (it == null) {
                cmd.errors.reject("Please answer question ${i + 1}")
            }
        }

        //TODO: validate answer data types

        if (!cmd.hasErrors()) {
            cmd.answers.eachWithIndex { it, i ->
                cmd.feedbackInstance.addToAnswers(new Answer(question: questions.get(i), score: it)).save(failOnError: true)
            }

            render(view: 'done')
        } else {
            render(view: 'questions', model: [cmd: cmd])
        }
    }

    def ajaxGetWardsForHealthService() {
        def list = []

        list << [value: '', text: 'Not Specified']

        List<Ward> wards = Ward.findAllByHealthService(HealthService.get(params.id))

        wards.each {
            list << [value: it.id, text: it.toString()]
        }

        render list as JSON
    }

    def ajaxGetCSDsForHealthService() {
        def list = []

        HealthService healthService = HealthService.get(params.id)

        if (healthService.type.name.equals("Hospital")) {
            List<ClinicalServiceDepartment> clinicalServiceDepartments = ClinicalServiceDepartment.list()

            clinicalServiceDepartments.each {
                list << [value: it.id, text: it.toString()]
            }
        }

        render list as JSON
    }

    def ajaxGetClinicians() {
        def list = []

        HealthService healthService = HealthService.get(params.long('healthService.id'))
        ClinicalServiceDepartment department = ClinicalServiceDepartment.get(params.long('clinicalServiceDepartment.id'))

        List<Clinician> clinicians = Clinician.findAllByHealthServiceAndClinicalServiceDepartment(healthService, department)

        list << [value: 'null', text: 'Other / Don\'t Know']

        clinicians.each {
            list << [value: it.id, text: it.name]
        }

        render list as JSON
    }

    def ajaxGetHealthServicesForType() {
        def list = []

        HealthServiceType healthServiceType = HealthServiceType.get(params.id)
        List<HealthService> healthServices = HealthService.findAllByType(healthServiceType)

        healthServices.each {
            list << [value: it.id, text: it.toString()]
        }

        render list as JSON
    }

    def ajaxGetHealthServices() {
        def healthServices = HealthService.createCriteria().list(max: 15) {
            if (params.long('healthServiceType.id')) {
                eq('type.id', params.long('healthServiceType.id'))
            }
            or {
                like('name', '%' + params.term + '%')
                like('postCode', params.term + '%')
            }
        }

        render healthServices.collect {[id: it.id, value: it.name, label: it.name + ', ' + it.postCode]} as JSON
    }

    @Secured('ROLE_ADMIN')
    def reviewComments() {
        params.max = Math.min(params.max ? params.int('max') : 20, 100)

        def visibility = params.visibility ? FeedbackVisibility.valueOf(params.visibility) : FeedbackVisibility.AWAITING

        List<Feedback> feedbacks = Feedback.createCriteria().list(params) {
            ne('npsReason', '')
            if (visibility == FeedbackVisibility.AWAITING) {
                isNull('npsReasonVisible')
            } else if (visibility == FeedbackVisibility.HIDDEN) {
                eq('npsReasonVisible', false)
            } else if (visibility == FeedbackVisibility.VISIBLE) {
                eq('npsReasonVisible', true)
            } else {
                throw new IllegalArgumentException();
            }
        }

        [feedbacks: feedbacks, totalCount: feedbacks.totalCount]
    }

    @Secured('ROLE_ADMIN')
    def showComment() {
        Feedback feedback = Feedback.get(params.id)
        feedback.setNpsReasonVisible(true)
        feedback.save()
        redirect(action: 'reviewComments', params: params)
    }

    @Secured('ROLE_ADMIN')
    def hideComment() {
        Feedback feedback = Feedback.get(params.id)
        feedback.setNpsReasonVisible(false)
        feedback.save()
        redirect(action: 'reviewComments', params: params)
    }
}

class QuestionCommand {
    Feedback feedbackInstance
    List<String> answers = new ArrayList<String>()
}