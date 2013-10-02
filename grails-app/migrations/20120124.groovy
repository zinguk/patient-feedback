databaseChangeLog = {
	changeSet(author: "lloyd.davies (generated)", id: "1327432948710-1") {
		addColumn(tableName: "health_service") {
			column(name: "post_code", type: "varchar(10)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "lloyd.davies (generated)", id: "1327432948710-2") {
		addColumn(tableName: "question") {
			column(name: "rank", type: "double") {
				constraints(nullable: "false")
			}
		}
	}
}
