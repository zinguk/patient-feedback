databaseChangeLog = {

	changeSet(author: "lloyd.davies (generated)", id: "1326999322501-1") {
		createTable(tableName: "answer") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "answerPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "feedback_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "question_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "value", type: "varchar(1100)") {
				constraints(nullable: "false")
			}

			column(name: "visible", type: "bit")

			column(name: "answers_idx", type: "integer")
		}
	}

	changeSet(author: "lloyd.davies (generated)", id: "1326999322501-2") {
		createTable(tableName: "clinical_service_department") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "clinical_servPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(80)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "lloyd.davies (generated)", id: "1326999322501-3") {
		createTable(tableName: "clinician") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "clinicianPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "clinical_service_department_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "health_service_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "lloyd.davies (generated)", id: "1326999322501-4") {
		createTable(tableName: "feedback") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "feedbackPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "anonymous", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "clinical_service_department_id", type: "bigint")

			column(name: "clinician_id", type: "bigint")

			column(name: "date_of_visit", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "email_address", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "first_name", type: "varchar(20)") {
				constraints(nullable: "false")
			}

			column(name: "health_service_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "phone_number", type: "varchar(15)") {
				constraints(nullable: "false")
			}

			column(name: "post_code", type: "varchar(10)") {
				constraints(nullable: "false")
			}

			column(name: "relationship", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "surname", type: "varchar(30)") {
				constraints(nullable: "false")
			}

			column(name: "ward_id", type: "bigint")
		}
	}

	changeSet(author: "lloyd.davies (generated)", id: "1326999322501-5") {
		createTable(tableName: "health_service") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "health_servicPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(80)") {
				constraints(nullable: "false")
			}

			column(name: "type_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "lloyd.davies (generated)", id: "1326999322501-6") {
		createTable(tableName: "health_service_type") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "health_servicPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "lloyd.davies (generated)", id: "1326999322501-7") {
		createTable(tableName: "question") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "questionPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "full_question", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "health_service_type_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "short_title", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "type", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "questions_idx", type: "integer")
		}
	}

	changeSet(author: "lloyd.davies (generated)", id: "1326999322501-8") {
		createTable(tableName: "role") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "rolePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "authority", type: "varchar(255)") {
				constraints(nullable: "false", unique: "true")
			}
		}
	}

	changeSet(author: "lloyd.davies (generated)", id: "1326999322501-9") {
		createTable(tableName: "user") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "userPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "account_expired", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "account_locked", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "enabled", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "password", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "password_expired", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "username", type: "varchar(255)") {
				constraints(nullable: "false", unique: "true")
			}
		}
	}

	changeSet(author: "lloyd.davies (generated)", id: "1326999322501-10") {
		createTable(tableName: "user_role") {
			column(name: "role_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "user_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "lloyd.davies (generated)", id: "1326999322501-11") {
		createTable(tableName: "ward") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "wardPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "health_service_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(80)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "lloyd.davies (generated)", id: "1326999322501-12") {
		addPrimaryKey(columnNames: "role_id, user_id", constraintName: "user_rolePK", tableName: "user_role")
	}

	changeSet(author: "lloyd.davies (generated)", id: "1326999322501-13") {
		createIndex(indexName: "FKABCA3FBE53B2D0D9", tableName: "answer") {
			column(name: "feedback_id")
		}
	}

	changeSet(author: "lloyd.davies (generated)", id: "1326999322501-14") {
		createIndex(indexName: "FKABCA3FBEA0BCE939", tableName: "answer") {
			column(name: "question_id")
		}
	}

	changeSet(author: "lloyd.davies (generated)", id: "1326999322501-15") {
		createIndex(indexName: "FK9D8F786E7FA11782", tableName: "clinician") {
			column(name: "health_service_id")
		}
	}

	changeSet(author: "lloyd.davies (generated)", id: "1326999322501-16") {
		createIndex(indexName: "FK9D8F786ECDCCDAA7", tableName: "clinician") {
			column(name: "clinical_service_department_id")
		}
	}

	changeSet(author: "lloyd.davies (generated)", id: "1326999322501-17") {
		createIndex(indexName: "FKF495EB851615D0F9", tableName: "feedback") {
			column(name: "ward_id")
		}
	}

	changeSet(author: "lloyd.davies (generated)", id: "1326999322501-18") {
		createIndex(indexName: "FKF495EB853B8D19B", tableName: "feedback") {
			column(name: "clinician_id")
		}
	}

	changeSet(author: "lloyd.davies (generated)", id: "1326999322501-19") {
		createIndex(indexName: "FKF495EB857FA11782", tableName: "feedback") {
			column(name: "health_service_id")
		}
	}

	changeSet(author: "lloyd.davies (generated)", id: "1326999322501-20") {
		createIndex(indexName: "FKF495EB85CDCCDAA7", tableName: "feedback") {
			column(name: "clinical_service_department_id")
		}
	}

	changeSet(author: "lloyd.davies (generated)", id: "1326999322501-21") {
		createIndex(indexName: "FK867E53D27D914974", tableName: "health_service") {
			column(name: "type_id")
		}
	}

	changeSet(author: "lloyd.davies (generated)", id: "1326999322501-22") {
		createIndex(indexName: "FKBA823BE62BF5207", tableName: "question") {
			column(name: "health_service_type_id")
		}
	}

	changeSet(author: "lloyd.davies (generated)", id: "1326999322501-23") {
		createIndex(indexName: "authority_unique_1326999322446", tableName: "role", unique: "true") {
			column(name: "authority")
		}
	}

	changeSet(author: "lloyd.davies (generated)", id: "1326999322501-24") {
		createIndex(indexName: "username_unique_1326999322451", tableName: "user", unique: "true") {
			column(name: "username")
		}
	}

	changeSet(author: "lloyd.davies (generated)", id: "1326999322501-25") {
		createIndex(indexName: "FK143BF46A252456B9", tableName: "user_role") {
			column(name: "role_id")
		}
	}

	changeSet(author: "lloyd.davies (generated)", id: "1326999322501-26") {
		createIndex(indexName: "FK143BF46ACA4F1A99", tableName: "user_role") {
			column(name: "user_id")
		}
	}

	changeSet(author: "lloyd.davies (generated)", id: "1326999322501-27") {
		createIndex(indexName: "FK37927C7FA11782", tableName: "ward") {
			column(name: "health_service_id")
		}
	}

	changeSet(author: "lloyd.davies (generated)", id: "1326999322501-28") {
		addForeignKeyConstraint(baseColumnNames: "feedback_id", baseTableName: "answer", constraintName: "FKABCA3FBE53B2D0D9", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "feedback", referencesUniqueColumn: "false")
	}

	changeSet(author: "lloyd.davies (generated)", id: "1326999322501-29") {
		addForeignKeyConstraint(baseColumnNames: "question_id", baseTableName: "answer", constraintName: "FKABCA3FBEA0BCE939", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "question", referencesUniqueColumn: "false")
	}

	changeSet(author: "lloyd.davies (generated)", id: "1326999322501-30") {
		addForeignKeyConstraint(baseColumnNames: "clinical_service_department_id", baseTableName: "clinician", constraintName: "FK9D8F786ECDCCDAA7", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "clinical_service_department", referencesUniqueColumn: "false")
	}

	changeSet(author: "lloyd.davies (generated)", id: "1326999322501-31") {
		addForeignKeyConstraint(baseColumnNames: "health_service_id", baseTableName: "clinician", constraintName: "FK9D8F786E7FA11782", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "health_service", referencesUniqueColumn: "false")
	}

	changeSet(author: "lloyd.davies (generated)", id: "1326999322501-32") {
		addForeignKeyConstraint(baseColumnNames: "clinical_service_department_id", baseTableName: "feedback", constraintName: "FKF495EB85CDCCDAA7", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "clinical_service_department", referencesUniqueColumn: "false")
	}

	changeSet(author: "lloyd.davies (generated)", id: "1326999322501-33") {
		addForeignKeyConstraint(baseColumnNames: "clinician_id", baseTableName: "feedback", constraintName: "FKF495EB853B8D19B", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "clinician", referencesUniqueColumn: "false")
	}

	changeSet(author: "lloyd.davies (generated)", id: "1326999322501-34") {
		addForeignKeyConstraint(baseColumnNames: "health_service_id", baseTableName: "feedback", constraintName: "FKF495EB857FA11782", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "health_service", referencesUniqueColumn: "false")
	}

	changeSet(author: "lloyd.davies (generated)", id: "1326999322501-35") {
		addForeignKeyConstraint(baseColumnNames: "ward_id", baseTableName: "feedback", constraintName: "FKF495EB851615D0F9", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "ward", referencesUniqueColumn: "false")
	}

	changeSet(author: "lloyd.davies (generated)", id: "1326999322501-36") {
		addForeignKeyConstraint(baseColumnNames: "type_id", baseTableName: "health_service", constraintName: "FK867E53D27D914974", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "health_service_type", referencesUniqueColumn: "false")
	}

	changeSet(author: "lloyd.davies (generated)", id: "1326999322501-37") {
		addForeignKeyConstraint(baseColumnNames: "health_service_type_id", baseTableName: "question", constraintName: "FKBA823BE62BF5207", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "health_service_type", referencesUniqueColumn: "false")
	}

	changeSet(author: "lloyd.davies (generated)", id: "1326999322501-38") {
		addForeignKeyConstraint(baseColumnNames: "role_id", baseTableName: "user_role", constraintName: "FK143BF46A252456B9", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "role", referencesUniqueColumn: "false")
	}

	changeSet(author: "lloyd.davies (generated)", id: "1326999322501-39") {
		addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "user_role", constraintName: "FK143BF46ACA4F1A99", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
	}

	changeSet(author: "lloyd.davies (generated)", id: "1326999322501-40") {
		addForeignKeyConstraint(baseColumnNames: "health_service_id", baseTableName: "ward", constraintName: "FK37927C7FA11782", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "health_service", referencesUniqueColumn: "false")
	}

	include file: '20120124.groovy'

	include file: '20120203.groovy'

	include file: '20120203-2.groovy'
}
