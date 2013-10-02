package zingit.patientfeedback

public enum Relationship {
    PATIENT('I am the patient'),
    RELATIVE('Relative of patient'),
    FRIEND('Friend of patient')

    final String value

    private Relationship(String value) {
        this.value = value
    }
}