package zingit.patientfeedback.util

class DateUtil {
    static Date monthsAgo(int months) {
        Calendar cal = new GregorianCalendar()
        cal.add(Calendar.MONTH, -months)
        return cal.time
    }
}
