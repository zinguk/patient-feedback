package zingit.patientfeedback

import grails.plugins.springsecurity.Secured
import org.springframework.dao.DataIntegrityViolationException

class WardController {
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def chartService

    def index() {
        redirect(action: "list", params: params)
    }

    @Secured('ROLE_ADMIN')
    def list() {
        params.max = Math.min(params.max ? params.int('max') : 20, 100)
        [wardInstanceList: Ward.list(params), wardInstanceTotal: Ward.count()]
    }

    @Secured('ROLE_ADMIN')
    def create() {
        [wardInstance: new Ward(params)]
    }

    @Secured('ROLE_ADMIN')
    def save() {
        def wardInstance = new Ward(params)
        if (!wardInstance.save(flush: true)) {
            render(view: "create", model: [wardInstance: wardInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'ward.label', default: 'Ward'), wardInstance.id])
        redirect(action: "show", id: wardInstance.id)
    }

    def show() {
        def wardInstance = Ward.get(params.id)
        if (!wardInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'ward.label', default: 'Ward'), params.id])
            redirect(action: "list")
            return
        }

        def monthlyRatings = chartService.getChartData([question_id: params.long('question.id'), ward_id: wardInstance.id])

        params.max = Math.min(params.max ? params.int('max') : 20, 100)
        List<Feedback> feedbacks = Feedback.createCriteria().list(params) {
            eq('ward', wardInstance)
            eq('npsReasonVisible', true)
            ne('npsReason', '')
        }

        [wardInstance: wardInstance, feedbacks: feedbacks, monthlyRatings: monthlyRatings]
    }

    @Secured('ROLE_ADMIN')
    def edit() {
        def wardInstance = Ward.get(params.id)
        if (!wardInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'ward.label', default: 'Ward'), params.id])
            redirect(action: "list")
            return
        }

        [wardInstance: wardInstance]
    }

    @Secured('ROLE_ADMIN')
    def update() {
        def wardInstance = Ward.get(params.id)
        if (!wardInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'ward.label', default: 'Ward'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (wardInstance.version > version) {
                wardInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'ward.label', default: 'Ward')] as Object[],
                          "Another user has updated this Ward while you were editing")
                render(view: "edit", model: [wardInstance: wardInstance])
                return
            }
        }

        wardInstance.properties = params

        if (!wardInstance.save(flush: true)) {
            render(view: "edit", model: [wardInstance: wardInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'ward.label', default: 'Ward'), wardInstance.id])
        redirect(action: "show", id: wardInstance.id)
    }

    @Secured('ROLE_ADMIN')
    def delete() {
        def wardInstance = Ward.get(params.id)
        if (!wardInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'ward.label', default: 'Ward'), params.id])
            redirect(action: "list")
            return
        }

        try {
            wardInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'ward.label', default: 'Ward'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'ward.label', default: 'Ward'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
