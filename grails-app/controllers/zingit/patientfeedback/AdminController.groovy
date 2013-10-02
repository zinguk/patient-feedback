package zingit.patientfeedback

import grails.plugins.springsecurity.Secured

class AdminController {
    @Secured('ROLE_ADMIN')
    def index() {

    }
}
