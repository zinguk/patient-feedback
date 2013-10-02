import zingit.patientfeedback.*

class BootStrap {
    def springSecurityService

    def init = { servletContext ->
        switch (grails.util.GrailsUtil.environment) {
            case "developmentXXX":
                Role adminRole = new Role(authority: 'ROLE_ADMIN').save(flush: true)
                User adminUser = new User(username: 'admin', password: 'admin', enabled: true).save(flush: true)
                UserRole.create(adminUser, adminRole, true)

                assert User.count() == 1
                assert Role.count() == 1
                assert UserRole.count() == 1

                HealthService broomfield = new HealthService(name: 'Broomfield HealthService').save(flush: true)
                new HealthService(name: 'St Michael\'s HealthService').save(flush: true)
                new HealthService(name: 'St Peter\'s HealthService').save(flush: true)
                
                assert HealthService.count() == 3

                new ClinicalServiceDepartment(name: 'Orthopaedics services').save(flush: true)
                new ClinicalServiceDepartment(name: 'Haematology services').save(flush: true)
                new ClinicalServiceDepartment(name: 'Pain management services').save(flush: true)

                assert ClinicalServiceDepartment.count() == 3

                new Ward(name: 'Emergency Short Stay', healthService: broomfield).save(flush: true)
                new Ward(name: 'Emergency Assessment Unit', healthService: broomfield).save(flush: true)
                new Ward(name: 'Danbury Ward', healthService: broomfield).save(flush: true)
                new Ward(name: 'Rayne Ward', healthService: broomfield).save(flush: true)
                new Ward(name: 'Delivery Suite', healthService: broomfield).save(flush: true)

                assert Ward.count() == 5
        }
    }
    def destroy = {
    }
}
