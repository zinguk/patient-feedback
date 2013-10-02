package zingit.patientfeedback

import java.math.RoundingMode

class PatientFeedbackTagLib {
    static namespace = 'pf'

    def stars = { attrs ->
        int width = attrs.value ? new BigDecimal(attrs.value).multiply(20) : 0
        def position = attrs.colour == 'red' ? 'center left' : 'bottom left'

        out << """
                <div>
                    <div style="float: left; padding-right: 5px">${new BigDecimal(attrs.value ?: 0).setScale(1, RoundingMode.HALF_UP)}</div>
                    <div style="float: left; text-align:left; padding: 0; margin: 0; background: url(/images/stars20.png); height: 20px; width: 200px;">
                        <div style="background: url(/images/stars20.png) ${position}; padding: 0; margin: 0; height: 20px; width: ${width}px;"></div>
                    </div>
                </div>
               """
    }
}
