package zingit.patientfeedback

class Ward {
    String name

    static belongsTo = [healthService: HealthService]

    static transients = ['rating','npsRating']

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

    Rating getRating() {
        List<Answer> answers = Answer.createCriteria().list {
            question {
                eq('type', QuestionType.RATING)
            }
            feedback {
                eq('ward', this)
            }
        }

        int totalScore = 0
        int totalCount = 0

        if (!answers.empty) {
            totalScore = answers.sum { Integer.valueOf(it.score) }
            totalCount = answers.size()
        }

        List<Feedback> feedbacks = Feedback.findAllByWard(this)

        if (!feedbacks.empty) {
            totalScore += feedbacks.sum {it.npsRating}
            totalCount += feedbacks.size()
        }

        BigDecimal rating = totalCount ? totalScore/totalCount : 0

        return new Rating(rating: rating, count: totalCount)
    }

    Rating getRatingForQuestion(Question question) {
        List<Answer> answers = Answer.createCriteria().list {
            eq('question', question)
            feedback {
                eq('ward', this)
            }
        }

        BigDecimal rating = answers.empty ? 0 : answers.sum { Integer.valueOf(it.score) } / answers.size()

        return new Rating(rating: rating, count: answers.size())
    }

    Rating getNpsRating() {
        List<Feedback> feedbacks = Feedback.findAllByWard(this)

        BigDecimal rating = feedbacks.empty ? 0 : feedbacks.sum { it.npsRating } / feedbacks.size()

        return new Rating(rating: rating, count: feedbacks.size())
    }
}
