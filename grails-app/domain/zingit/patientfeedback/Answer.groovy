package zingit.patientfeedback

class Answer {
    Question question
    Integer score

    static belongsTo = [feedback: Feedback]

    static constraints = {
        score(range: 1..10)
    }
}
