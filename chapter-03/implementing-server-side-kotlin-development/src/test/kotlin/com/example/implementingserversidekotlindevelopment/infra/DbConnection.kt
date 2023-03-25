package com.example.implementingserversidekotlindevelopment.infra

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import javax.sql.DataSource

/**
 * Db connection
 *
 * DB 統合テストで DB 設定をおこなうメソッド
 *
 * @constructor Create empty Db connection
 */
object DbConnection {
    /**
     * テスト時に DB 設定をするメソッド
     *
     * @return
     */
    fun dataSource(): DataSource {
        val hikariConfig = HikariConfig()
        hikariConfig.jdbcUrl = "jdbc:postgresql://127.0.0.1:5432/sample-db"
        hikariConfig.username = "sample-user"
        hikariConfig.password = "sample-pass"
        hikariConfig.connectionTimeout = java.lang.Long.valueOf(500)
        hikariConfig.isAutoCommit = true
        hikariConfig.transactionIsolation = "TRANSACTION_READ_COMMITTED"
        hikariConfig.poolName = "realworldPool01"
        hikariConfig.maximumPoolSize = 10
        return HikariDataSource(hikariConfig)
    }

    val namedParameterJdbcTemplate = NamedParameterJdbcTemplate(dataSource())

    /**
     * テスト時にテーブルのシーケンスを設定するメソッド
     *
     * 全てのテスト実行前に呼び出すことで、該当カラムの初期値が固定される
     *
     */
    fun resetSequence() {
        val sequenceValue = 10000
        val sql = """
            SELECT
                setval('articles_id_seq', $sequenceValue)
            ;
        """.trimIndent()
        namedParameterJdbcTemplate.queryForList(sql, MapSqlParameterSource())
    }
}
