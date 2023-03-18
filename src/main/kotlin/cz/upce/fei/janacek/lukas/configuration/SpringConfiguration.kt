package cz.upce.fei.janacek.lukas.configuration

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories


@Configuration
@EnableJpaRepositories(PROJECT_MAIN_PACKAGE)
@EntityScan(PROJECT_MAIN_PACKAGE)
@ComponentScan(PROJECT_MAIN_PACKAGE)
class SpringConfiguration
