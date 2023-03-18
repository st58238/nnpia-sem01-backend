package cz.upce.fei.janacek.lukas.lib

import java.lang.Exception
import java.sql.DriverManager

object MigrationFunctionLibrary {

    fun prePass(args: Array<out String>): Array<out String> {
        val returned = mutableListOf<String>()
        if ("migration" in args) {
            args.filter { it != "migration" }.toCollection(returned)
            migrationActive()
        }
        return returned.toTypedArray()
    }

    @Suppress("SqlResolve")
    private fun migrationActive() {
        val connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "nnpiaSem01", "nnpiaSem01SecurePlaintextPassword")
        try {
            connection.createStatement().execute(connection.nativeSQL("CREATE DATABASE sem01;"))
        } catch (_: Exception) {}
        connection.createStatement().execute(connection.nativeSQL("CREATE DATABASE migration;"))
        Runtime.getRuntime().addShutdownHook(Thread {
            connection.createStatement().execute(connection.nativeSQL("DROP DATABASE migration;"))
        })
    }
}