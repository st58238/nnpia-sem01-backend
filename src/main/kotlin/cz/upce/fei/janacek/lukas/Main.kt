package cz.upce.fei.janacek.lukas

import cz.upce.fei.janacek.lukas.configuration.SpringConfiguration
import cz.upce.fei.janacek.lukas.migration.MigrationFunctionLibrary.checkForMigration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import


@SpringBootApplication
@Import(SpringConfiguration::class)
class Main

fun main(args: Array<out String>) {
    val remainingArgs = checkForMigration(args)
    runApplication<Main>(*remainingArgs)
}