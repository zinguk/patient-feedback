package zingit.patientfeedback

import groovy.sql.Sql

class HealthService {
    def dataSource
    
    String name
    HealthServiceType type
    String postCode

    static constraints = {
        name(blank: false, maxSize: 80)
        type(nullable:  false)
        postCode(blank: false, maxSize: 10)
    }

    static mapping = {
        sort('name')
    }
    
    static hasMany = [wards: Ward]

    static transients = ['rating', 'npsRating']

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
                eq('healthService', this)
            }
        }

        int totalScore = 0
        int totalCount = 0

        if (!answers.empty) {
            totalScore = answers.sum { Integer.valueOf(it.score) }
            totalCount = answers.size()
        }
        
        List<Feedback> feedbacks = Feedback.findAllByHealthService(this)

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
                eq('healthService', this)
            }
        }

        BigDecimal rating = answers.empty ? 0 : answers.sum { Integer.valueOf(it.score) } / answers.size()

        return new Rating(rating: rating, count: answers.size())
    }

    Rating getRatingForCSD(ClinicalServiceDepartment clinicalServiceDepartment) {
        List<Answer> answers = Answer.createCriteria().list {
            question {
                eq('type', QuestionType.RATING)
            }
            feedback {
                eq('healthService', this)
                eq('clinicalServiceDepartment', clinicalServiceDepartment)
            }
        }

        int totalScore = 0
        int totalCount = 0

        if (!answers.empty) {
            totalScore = answers.sum { Integer.valueOf(it.score) }
            totalCount = answers.size()
        }

        List<Feedback> feedbacks = Feedback.findAllByHealthServiceAndClinicalServiceDepartment(this, clinicalServiceDepartment)

        if (!feedbacks.empty) {
            totalScore += feedbacks.sum {it.npsRating}
            totalCount += feedbacks.size()
        }

        BigDecimal rating = totalCount ? totalScore/totalCount : 0

        return new Rating(rating: rating, count: totalCount)
    }

    Rating getNpsRating() {
        List<Feedback> feedbacks = Feedback.findAllByHealthService(this)

        BigDecimal rating = feedbacks.empty ? 0 : feedbacks.sum { it.npsRating } / feedbacks.size()

        return new Rating(rating: rating, count: feedbacks.size())
    }
}
