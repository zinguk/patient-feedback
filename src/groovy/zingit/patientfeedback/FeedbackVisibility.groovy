package zingit.patientfeedback

public enum FeedbackVisibility {
    AWAITING('Awaiting Approval'),
    VISIBLE('Visible'),
    HIDDEN('Hidden')

    final String value

    private FeedbackVisibility(String value) {
        this.value = value
    }
}