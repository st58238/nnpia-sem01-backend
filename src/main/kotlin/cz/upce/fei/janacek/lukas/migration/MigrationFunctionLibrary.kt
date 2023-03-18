package cz.upce.fei.janacek.lukas.migration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.lang.Exception
import java.sql.DriverManager

@Profile("migration")
@Component
internal object MigrationFunctionLibrary {

    @Value("\${spring.datasource.username}")
    private var username: String? = null
    @Value("\${spring.datasource.password}")
    private var password: String? = null

    fun checkForMigration(args: Array<out String>): Array<out String> {
        val returned = mutableListOf<String>()
        if ("migration" in args) {
            args.filter { it != "migration" }.toCollection(returned)
            migrationActive()
            return returned.toTypedArray()
        }
        return args
    }

    @Suppress("SqlResolve")
    private fun migrationActive() {
        val connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", username ?: "nnpiaSem01", password ?: "nnpiaSem01SecurePlaintextPassword")
        try {
            connection.createStatement().execute(connection.nativeSQL("CREATE DATABASE sem01;"))
        } catch (_: Exception) {}

        connection.createStatement().execute(connection.nativeSQL("CREATE DATABASE migration;"))

        Runtime.getRuntime().addShutdownHook(Thread {
            connection.createStatement().execute(connection.nativeSQL("DROP DATABASE migration;"))
        })
    }
}