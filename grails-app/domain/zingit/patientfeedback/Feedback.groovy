package zingit.patientfeedback

class Feedback {
    HealthService healthService
    Ward ward
    boolean anonymous
    Relationship relationship
    String firstName
    String surname
    String postCode
    //String nhsNumber
    //String patientNumber
    Date dateOfVisit
    String emailAddress
    String phoneNumber
    ClinicalServiceDepartment clinicalServiceDepartment
    List<Answer> answers
    Clinician clinician
    int npsRating
    String npsReason
    Boolean npsReasonVisible

    static hasMany = [answers: Answer]

    static constraints = {
        healthService()
        ward(nullable: true)
        anonymous()
        relationship()
        firstName(blank: false, maxSize: 20)
        surname(blank: false, maxSize: 30)
        postCode(blank: false, maxSize: 10)
        //nhsNumber(blank: false, minSize: 12, maxSize: 12)
        //patientNumber(blank: true, maxSize: 20)
        dateOfVisit(attributes: [precision: 'day', years: (2011..2020)])
        emailAddress(blank: true, email: true)
        phoneNumber(blank: true, maxSize: 15)
        clinicalServiceDepartment(nullable: true)
        clinician(nullable: true)
        npsRating(range: 1..10)
        npsReason(blank: true, maxSize: 1100)
        npsReasonVisible(nullable: true)
    }
}
