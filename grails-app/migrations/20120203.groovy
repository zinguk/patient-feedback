databaseChangeLog = {

	changeSet(author: "lloyd.davies (generated)", id: "1328290694467-1") {
		addColumn(tableName: "feedback") {
			column(name: "nps_rating", type: "integer") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "lloyd.davies (generated)", id: "1328290694467-2") {
		addColumn(tableName: "feedback") {
			column(name: "nps_reason", type: "varchar(1100)") {
				constraints(nullable: "false")
			}
		}
	}

    changeSet(author: "lloyd.davies (generated)", id: "1328291237708-1") {
        addColumn(tableName: "feedback") {
            column(name: "nps_reason_visible", type: "bit") {
                constraints(nullable: "true")
            }
        }
    }
}
