package cz.upce.fei.janacek.lukas.configuration

import cz.upce.fei.janacek.lukas.migration.MigrationInitService
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.scheduling.annotation.EnableAsync

const val PROJECT_MAIN_PACKAGE = "cz.upce.fei.janacek.lukas"

@EnableAsync
@Configuration
@Import(
    MigrationInitService::class
)
@ComponentScan(PROJECT_MAIN_PACKAGE)
class BasicConfiguration