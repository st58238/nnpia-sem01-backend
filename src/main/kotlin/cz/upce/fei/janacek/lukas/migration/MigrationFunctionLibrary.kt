package cz.upce.fei.janacek.lukas.migration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.sql.DriverManager
import java.util.*
import kotlin.system.exitProcess

@Profile("migration")
@Component
internal object MigrationFunctionLibrary {

    @Value("\${spring.datasource.username}")
    var username: String? = null
    @Value("\${spring.datasource.password}")
    var password: String? = null

    fun preProcessMigration(args: Array<out String>): Array<out String> {
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

        connection.createStatement().execute(connection.nativeSQL("CREATE DATABASE migration;"))

        Runtime.getRuntime().addShutdownHook(Thread {
            connection.createStatement().execute(connection.nativeSQL("DROP DATABASE migration;"))
        })
    }

    fun postProcessMigration(context: ConfigurableApplicationContext, vararg args: String): Array<out String> {
        if ("migration" in context.environment.activeProfiles)
            Timer().schedule(object : TimerTask() {
                override fun run() {
                    context.close()
                    exitProcess(0)
                }
            }, 4000)
        return args
    }
}