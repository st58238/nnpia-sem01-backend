package cz.upce.fei.janacek.lukas

import cz.upce.fei.janacek.lukas.configuration.SpringConfiguration
import cz.upce.fei.janacek.lukas.lib.Preparations
import cz.upce.fei.janacek.lukas.migration.MigrationFunctionLibrary.postProcessMigration
import cz.upce.fei.janacek.lukas.migration.MigrationFunctionLibrary.preProcessMigration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import


@SpringBootApplication
@Import(SpringConfiguration::class)
class Main

fun main(args: Array<out String>) {
    Preparations.ensurePreparedness()
    val remainingArgs = preProcessMigration(args)

    val context = runApplication<Main>(*remainingArgs)

    postProcessMigration(context, *remainingArgs)
}

