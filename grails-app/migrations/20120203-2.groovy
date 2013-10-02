databaseChangeLog = {

	changeSet(author: "lloyd.davies (generated)", id: "1328294454838-1") {
		addColumn(tableName: "answer") {
			column(name: "score", type: "integer") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "lloyd.davies (generated)", id: "1328294454838-2") {
		dropColumn(columnName: "value", tableName: "answer")
	}

	changeSet(author: "lloyd.davies (generated)", id: "1328294454838-3") {
		dropColumn(columnName: "visible", tableName: "answer")
	}
}
