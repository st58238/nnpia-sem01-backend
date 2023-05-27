package cz.upce.fei.janacek.lukas

import cz.upce.fei.janacek.lukas.configuration.BasicConfiguration
import cz.upce.fei.janacek.lukas.configuration.SpringConfiguration
import cz.upce.fei.janacek.lukas.lib.Preparations
import cz.upce.fei.janacek.lukas.migration.MigrationFunctionLibrary.postProcessMigration
import cz.upce.fei.janacek.lukas.migration.MigrationFunctionLibrary.preProcessMigration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import


@SpringBootApplication(
    exclude = [SecurityAutoConfiguration::class]
)
@Import(BasicConfiguration::class, SpringConfiguration::class)
class Main

fun main(args: Array<out String>) {
    Preparations.ensurePreparedness()
    val remainingArgs = preProcessMigration(args)

    val context = runApplication<Main>(*remainingArgs)

    postProcessMigration(context, *remainingArgs)
}

// DONE: package auth převést do struktury configuration, atd.
// DONE: schéma fix
// TODO: schéma db do obrázku
// TODO: Readme.md
// TODO: profile in frontened
// TODO: profile image: https://www.npmjs.com/package/react-avatar