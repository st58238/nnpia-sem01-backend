package cz.upce.fei.janacek.lukas.lib

import cz.upce.fei.janacek.lukas.migration.MigrationFunctionLibrary
import org.springframework.context.annotation.Profile
import java.sql.DriverManager

object Preparations {

    @Profile("migration")
    fun ensurePreparedness() {
        val connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", MigrationFunctionLibrary.username
            ?: "nnpiaSem01", MigrationFunctionLibrary.password ?: "nnpiaSem01SecurePlaintextPassword")
        try {
            connection.createStatement().execute(connection.nativeSQL("CREATE DATABASE sem01;"))
        } catch (_: Exception) {}
    }
}
