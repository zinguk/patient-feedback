package zingit.patientfeedback

import groovy.sql.Sql

class ChartService {
    def dataSource

    def getChartData(Map map) {
        def nullKeys = []
        map.keySet().each {
            if (map[it] == null) {
                nullKeys << it
            }
        }

        nullKeys.each {
            map.remove(it)
        }

        def sql = "select year(date_of_visit) as year, month(date_of_visit) as month, sum(cast(score as unsigned))/count(1) as overall, count(1) as count from feedback f join answer a on f.id = a.feedback_id "
        
        if (!map.empty) {
            sql += "where "
            
            map.keySet().eachWithIndex { it, i ->
                if (map[it]) {
                    sql += "${it} = :${it} and "
                }
            }
        }

        sql += "question_id in (select id from question where type = 'RATING') "
        sql += "group by year(date_of_visit), month(date_of_visit)"
            
        if (map.size() == 0) {
            new Sql(dataSource).rows(sql)
        } else {
            new Sql(dataSource).rows(sql, map)
        }
    }
}
