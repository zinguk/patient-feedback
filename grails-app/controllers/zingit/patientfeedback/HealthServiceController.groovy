package zingit.patientfeedback

import grails.plugins.springsecurity.Secured
import org.springframework.dao.DataIntegrityViolationException

class HealthServiceController {
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def chartService

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 20, 100)
        [healthServiceInstanceList: HealthService.list(params), healthServiceInstanceTotal: HealthService.count()]
    }

    @Secured(['ROLE_ADMIN'])
    def create() {
        [healthServiceInstance: new HealthService(params)]
    }

    @Secured(['ROLE_ADMIN'])
    def save() {
        def healthServiceInstance = new HealthService(params)
        if (!healthServiceInstance.save(flush: true)) {
            render(view: "create", model: [healthServiceInstance: healthServiceInstance])
            return
        }

		flash.message = healthServiceInstance.name + ' created'
        redirect(action: "show", id: healthServiceInstance.id)
    }

    def show() {
        def healthServiceInstance = HealthService.get(params.id)
        if (!healthServiceInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'healthService.label', default: 'HealthService'), params.id])
            redirect(action: "list")
            return
        }

        def monthlyRatings = chartService.getChartData([question_id: params.long('question.id'), health_service_id: healthServiceInstance.id])
        def nationalMonthlyRatings = chartService.getChartData([question_id: params.long('question.id')])

        List<ClinicalServiceDepartment> clinicalServiceDepartments = Feedback.createCriteria().list {
            eq('healthService', healthServiceInstance)
            projections {
                distinct "clinicalServiceDepartment"
            }
        }

        params.max = Math.min(params.max ? params.int('max') : 20, 100)
        List<Feedback> feedbacks = Feedback.createCriteria().list(params) {
            eq('healthService', healthServiceInstance)
            eq('npsReasonVisible', true)
            ne('npsReason', '')
        }

        [healthServiceInstance: healthServiceInstance, feedbacks: feedbacks, clinicalServiceDepartments: clinicalServiceDepartments, monthlyRatings: monthlyRatings, nationalMonthlyRatings: nationalMonthlyRatings]
    }

    @Secured(['ROLE_ADMIN'])
    def edit() {
        def healthServiceInstance = HealthService.get(params.id)
        if (!healthServiceInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'healthService.label', default: 'HealthService'), params.id])
            redirect(action: "list")
            return
        }

        [healthServiceInstance: healthServiceInstance]
    }

    @Secured(['ROLE_ADMIN'])
    def update() {
        def healthServiceInstance = HealthService.get(params.id)
        if (!healthServiceInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'healthService.label', default: 'HealthService'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (healthServiceInstance.version > version) {
                healthServiceInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'healthService.label', default: 'HealthService')] as Object[],
                          "Another user has updated this HealthService while you were editing")
                render(view: "edit", model: [healthServiceInstance: healthServiceInstance])
                return
            }
        }

        healthServiceInstance.properties = params

        if (!healthServiceInstance.save(flush: true)) {
            render(view: "edit", model: [healthServiceInstance: healthServiceInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'healthService.label', default: 'HealthService'), healthServiceInstance.id])
        redirect(action: "show", id: healthServiceInstance.id)
    }

    @Secured(['ROLE_ADMIN'])
    def delete() {
        def healthServiceInstance = HealthService.get(params.id)
        if (!healthServiceInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'healthService.label', default: 'HealthService'), params.id])
            redirect(action: "list")
            return
        }

        try {
            healthServiceInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'healthService.label', default: 'HealthService'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'healthService.label', default: 'HealthService'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
