package zingit.patientfeedback

class Clinician {
    String name
    HealthService healthService
    ClinicalServiceDepartment clinicalServiceDepartment

    static constraints = {
        name(blank: false)
        healthService()
        clinicalServiceDepartment()
    }

    static mapping = {
        sort 'name'
    }
}
