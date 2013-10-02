package zingit.patientfeedback

class HealthServiceType {
    String name
    List<Question> questions;

    static hasMany = [questions: Question]

    static constraints = {
        name(blank: false)
    }

    @Override
    String toString() {
        name
    }
}
