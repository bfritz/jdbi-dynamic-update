package com.bfritz.example

import org.skife.jdbi.v2.DBI
//import org.skife.jdbi.v2.logging.PrintStreamLog
//import org.skife.jdbi.v2.logging.SLF4JLog
import scala.collection.JavaConversions._
import resource._

object JdbiDynamicUpdateApp extends App {

    val dbi = new DBI("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1")
    //dbi.setSQLLog(new PrintStreamLog)

    def createTable() {
      for (h <- managed(dbi.open)) {
        h.execute("""
          CREATE TABLE users (
            id bigint primary key,
            first varchar(100),
            last varchar(100),
            email varchar(100),
            created timestamp not null
          )""")
        h.commit
      }
    }

    def insertJohnDoe() {
      for (h <- managed(dbi.open)) {
        h.createStatement("""
          INSERT INTO users (id, first, last, email, created)
          VALUES (1, 'J', 'D', 'john@example.com', current_timestamp)""")
          .execute
        h.commit
      }
    }

    def showUser(userId: Long) {
      for (h <- managed(dbi.open)) {
        println(
          h.select("SELECT * FROM users WHERE id = ?", userId.asInstanceOf[Object]))
      }
    }

    def update(userId: Long, params: Map[String,Any]) {
      val setClause = params.keys map { k => "%s = :%s" format (k, k) } mkString(",")
      for (h <- managed(dbi.open)) {
        h.createStatement(s"UPDATE users SET $setClause WHERE id = :id")
          .bindFromMap(params + ("id" -> userId))
          .execute
        h.commit
      }
    }

    createTable
    insertJohnDoe
    showUser(1)
    update(1, Map("first" -> "John", "last" -> "Doe", "email" -> "jdoe@example.com"))
    showUser(1)
    update(1, Map("first" -> "Johnny"))
    showUser(1)
}
