package zingit.patientfeedback

import grails.plugins.springsecurity.Secured

@Secured('ROLE_ADMIN')
class HealthServiceTypeController {
    def scaffold = true
}
