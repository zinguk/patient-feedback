package zingit.patientfeedback

import grails.plugins.springsecurity.Secured
import org.springframework.dao.DataIntegrityViolationException

class ClinicalServiceDepartmentController {
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def chartService

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 20, 100)
        [clinicalServiceDepartmentInstanceList: ClinicalServiceDepartment.list(params), clinicalServiceDepartmentInstanceTotal: ClinicalServiceDepartment.count()]
    }

    @Secured(['ROLE_ADMIN'])
    def create() {
        [clinicalServiceDepartmentInstance: new ClinicalServiceDepartment(params)]
    }

    @Secured(['ROLE_ADMIN'])
    def save() {
        def clinicalServiceDepartmentInstance = new ClinicalServiceDepartment(params)
        if (!clinicalServiceDepartmentInstance.save(flush: true)) {
            render(view: "create", model: [clinicalServiceDepartmentInstance: clinicalServiceDepartmentInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'clinicalServiceDepartment.label', default: 'ClinicalServiceDepartment'), clinicalServiceDepartmentInstance.id])
        redirect(action: "show", id: clinicalServiceDepartmentInstance.id)
    }

    def show() {
        def clinicalServiceDepartmentInstance = ClinicalServiceDepartment.get(params.id)
        if (!clinicalServiceDepartmentInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'clinicalServiceDepartment.label', default: 'ClinicalServiceDepartment'), params.id])
            redirect(action: "list")
            return
        }

        List<HealthService> healthServices = Feedback.createCriteria().list {
            eq('clinicalServiceDepartment', clinicalServiceDepartmentInstance)
            projections {
                distinct "healthService"
            }
        }

        [clinicalServiceDepartmentInstance: clinicalServiceDepartmentInstance, healthServices: healthServices]
    }

    def showHospital() {
        def clinicalServiceDepartmentInstance = ClinicalServiceDepartment.get(params.id)
        if (!clinicalServiceDepartmentInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'clinicalServiceDepartment.label', default: 'ClinicalServiceDepartment'), params.id])
            redirect(action: "list")
            return
        }

        def healthServiceInstance = HealthService.get(params.healthServiceId)
        if (!healthServiceInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'healthServiceInstance.label', default: 'HealthService'), params.healthServiceId])
            redirect(action: "list")
            return
        }

        def monthlyRatings = chartService.getChartData([question_id: params.long('question.id'), clinical_service_department_id: clinicalServiceDepartmentInstance.id, health_service_id: healthServiceInstance.id])

        params.max = Math.min(params.max ? params.int('max') : 20, 100)
        List<Feedback> feedbacks = Feedback.createCriteria().list(params) {
            eq('clinicalServiceDepartment', clinicalServiceDepartmentInstance)
            eq('npsReasonVisible', true)
            ne('npsReason', '')
        }

        [clinicalServiceDepartmentInstance: clinicalServiceDepartmentInstance, healthServiceInstance: healthServiceInstance, feedbacks: feedbacks, monthlyRatings: monthlyRatings]
    }

    @Secured(['ROLE_ADMIN'])
    def edit() {
        def clinicalServiceDepartmentInstance = ClinicalServiceDepartment.get(params.id)
        if (!clinicalServiceDepartmentInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'clinicalServiceDepartment.label', default: 'ClinicalServiceDepartment'), params.id])
            redirect(action: "list")
            return
        }

        [clinicalServiceDepartmentInstance: clinicalServiceDepartmentInstance]
    }

    @Secured(['ROLE_ADMIN'])
    def update() {
        def clinicalServiceDepartmentInstance = ClinicalServiceDepartment.get(params.id)
        if (!clinicalServiceDepartmentInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'clinicalServiceDepartment.label', default: 'ClinicalServiceDepartment'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (clinicalServiceDepartmentInstance.version > version) {
                clinicalServiceDepartmentInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'clinicalServiceDepartment.label', default: 'ClinicalServiceDepartment')] as Object[],
                        "Another user has updated this ClinicalServiceDepartment while you were editing")
                render(view: "edit", model: [clinicalServiceDepartmentInstance: clinicalServiceDepartmentInstance])
                return
            }
        }

        clinicalServiceDepartmentInstance.properties = params

        if (!clinicalServiceDepartmentInstance.save(flush: true)) {
            render(view: "edit", model: [clinicalServiceDepartmentInstance: clinicalServiceDepartmentInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'clinicalServiceDepartment.label', default: 'ClinicalServiceDepartment'), clinicalServiceDepartmentInstance.id])
        redirect(action: "show", id: clinicalServiceDepartmentInstance.id)
    }

    @Secured(['ROLE_ADMIN'])
    def delete() {
        def clinicalServiceDepartmentInstance = ClinicalServiceDepartment.get(params.id)
        if (!clinicalServiceDepartmentInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'clinicalServiceDepartment.label', default: 'ClinicalServiceDepartment'), params.id])
            redirect(action: "list")
            return
        }

        try {
            clinicalServiceDepartmentInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'clinicalServiceDepartment.label', default: 'ClinicalServiceDepartment'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'clinicalServiceDepartment.label', default: 'ClinicalServiceDepartment'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
