package zingit.patientfeedback

import org.springframework.dao.DataIntegrityViolationException
import grails.plugins.springsecurity.Secured

@Secured('ROLE_ADMIN')
class QuestionController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(QuestionListFilter filter) {
        List<HealthServiceType> healthServiceTypes = HealthServiceType.list()
        filter.healthServiceType = filter.healthServiceType ?: healthServiceTypes.first()
        def questionInstanceList = Question.findAllByHealthServiceType(filter.healthServiceType)
        [questionInstanceList: questionInstanceList, filter: filter, healthServiceTypes: healthServiceTypes]
    }

    def create() {
        [questionInstance: new Question(params)]
    }

    def save() {
        def questionInstance = new Question(params)
        if (!questionInstance.save(flush: true)) {
            render(view: "create", model: [questionInstance: questionInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'question.label', default: 'Question'), questionInstance.id])
        redirect(action: "show", id: questionInstance.id)
    }

    def show() {
        def questionInstance = Question.get(params.id)
        if (!questionInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'question.label', default: 'Question'), params.id])
            redirect(action: "list")
            return
        }

        [questionInstance: questionInstance]
    }

    def edit() {
        def questionInstance = Question.get(params.id)
        if (!questionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'question.label', default: 'Question'), params.id])
            redirect(action: "list")
            return
        }

        [questionInstance: questionInstance]
    }

    def update() {
        def questionInstance = Question.get(params.id)
        if (!questionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'question.label', default: 'Question'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (questionInstance.version > version) {
                questionInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'question.label', default: 'Question')] as Object[],
                          "Another user has updated this Question while you were editing")
                render(view: "edit", model: [questionInstance: questionInstance])
                return
            }
        }

        questionInstance.properties = params

        if (!questionInstance.save(flush: true)) {
            render(view: "edit", model: [questionInstance: questionInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'question.label', default: 'Question'), questionInstance.id])
        redirect(action: "show", id: questionInstance.id)
    }

    def delete() {
        def questionInstance = Question.get(params.id)
        if (!questionInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'question.label', default: 'Question'), params.id])
            redirect(action: "list")
            return
        }

        try {
            questionInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'question.label', default: 'Question'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'question.label', default: 'Question'), params.id])
            redirect(action: "show", id: params.id)
        }
    }

    def updateRank() {
        Question question = Question.get(params.id)
        Question prev = Question.get(params.long('after'))
        Question next = Question.get(params.long('before'))

        if (prev == null && next == null) {
            question.rank = 1
        } else if (prev == null) {
            question.rank = next.rank / 2
        } else if (next == null) {
            question.rank = prev.rank + 1
        } 	else if (prev.rank == 0 || next.rank == 0) {
            throw new IllegalStateException()
        } else {
            question.rank = prev.rank + (next.rank - prev.rank) / 2
        }

        question.save(failOnError: true)

        render "ok"
    }
}

class QuestionListFilter {
    HealthServiceType healthServiceType
}