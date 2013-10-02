package zingit.patientfeedback.util

public enum Question {
    question1('Clean environment'),
    question2('Staff teamwork'),
    question3('Consultant/doctor respect'),
    question4('Nursing staff respect'),
    question5('Other staff respect'),
    question6('Keeping you informed'),
    question7('Food and drink'),
    question8('Toilet assistance'),
    question9('Recommend to relative/friend')
    
    final String value

    private Question(String value) {
        this.value = value
    }
}