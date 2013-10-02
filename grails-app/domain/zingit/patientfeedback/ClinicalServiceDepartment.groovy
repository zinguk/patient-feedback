package zingit.patientfeedback

class ClinicalServiceDepartment {
    String name

    static constraints = {
        name(blank: false, maxSize: 80)
    }

    static mapping = {
        sort('name')
    }

    @Override
    String toString() {
        name
    }

    Rating getRatingForQuestionAndHealthService(Question question, HealthService healthService) {
        List<Answer> answers = Answer.createCriteria().list {
            eq('question', question)
            feedback {
                eq('healthService', healthService)
                eq('clinicalServiceDepartment', this)
            }
        }

        BigDecimal rating = answers.empty ? 0 : answers.sum { Integer.valueOf(it.score) } / answers.size()

        return new Rating(rating: rating, count: answers.size())
    }

    Rating getRatingForHealthService(HealthService healthService) {
        List<Answer> answers = Answer.createCriteria().list {
            question {
                eq('type', QuestionType.RATING)
            }
            feedback {
                eq('healthService', healthService)
                eq('clinicalServiceDepartment', this)
            }
        }

        int totalScore = 0
        int totalCount = 0

        if (!answers.empty) {
            totalScore = answers.sum { Integer.valueOf(it.score) }
            totalCount = answers.size()
        }

        List<Feedback> feedbacks = Feedback.findAllByHealthServiceAndClinicalServiceDepartment(healthService, this)

        if (!feedbacks.empty) {
            totalScore += feedbacks.sum {it.npsRating}
            totalCount += feedbacks.size()
        }

        BigDecimal rating = totalCount ? totalScore/totalCount : 0

        return new Rating(rating: rating, count: totalCount)
    }

    Rating getNpsRatingForHealthService(HealthService healthService) {
        List<Feedback> feedbacks = Feedback.createCriteria().list {
            eq('healthService', healthService)
            eq('clinicalServiceDepartment', this)
        }

        BigDecimal rating = feedbacks.empty ? 0 : feedbacks.sum { it.npsRating } / feedbacks.size()

        return new Rating(rating: rating, count: feedbacks.size())
    }
}
