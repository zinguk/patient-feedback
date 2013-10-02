package zingit.patientfeedback

class Question {
    QuestionType type
    HealthServiceType healthServiceType
    String shortTitle
    String fullQuestion
    double rank

    static constraints = {
        healthServiceType()
        shortTitle(blank: false)
        fullQuestion(blank: false)
    }

    static mapping = {
        sort 'rank'
    }

    def beforeInsert = {
        rank = determineRank()
    }

    private def determineRank() {
        def results = createCriteria().list {
            projections {
                min "rank"
            }
        }
        if (results.empty || results.get(0) == null) {
            return 1024
        } else {
            return results.get(0) / 2
        }
    }
}
