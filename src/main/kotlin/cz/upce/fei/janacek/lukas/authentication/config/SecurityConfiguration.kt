package cz.upce.fei.janacek.lukas.authentication.config

import cz.upce.fei.janacek.lukas.authentication.service.UserDetailService
import cz.upce.fei.janacek.lukas.authentication.util.JwtTokenUtil
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfiguration (
    private val userDetailService: UserDetailService,
    private val jwtTokenUtil: JwtTokenUtil
) {


    private fun authManager(http: HttpSecurity): AuthenticationManager {
        val authenticationManagerBuilder = http.getSharedObject(
            AuthenticationManagerBuilder::class.java
        )
        authenticationManagerBuilder.userDetailsService(userDetailService)
        return authenticationManagerBuilder.build()
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        val authenticationManager = authManager(http)
        http.authorizeRequests().antMatchers("/users/create", "/login")
            .permitAll().anyRequest().authenticated().and().csrf().disable()
            .authenticationManager(authenticationManager)
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .addFilter(UserAuthenticationFilter(authenticationManager, jwtTokenUtil))
            .addFilter(UserAuthorizationFilter(userDetailService, authenticationManager, jwtTokenUtil))

        return http.build()
    }

    @Bean
    fun BCryptPasswordEncoder(): BCryptPasswordEncoder {
        return org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder()
    }

    companion object {
        internal const val AUTHORIZATION = "Authorization"
        internal const val PREFIX = "Bearer "
        internal const val EXPIRATION_IN_MILLIS = 5 * 3_600_000
    }
}