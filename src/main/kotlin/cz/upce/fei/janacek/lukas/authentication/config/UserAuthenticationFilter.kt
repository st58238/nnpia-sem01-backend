package cz.upce.fei.janacek.lukas.authentication.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import cz.upce.fei.janacek.lukas.authentication.dto.LoginDto
import cz.upce.fei.janacek.lukas.authentication.util.JwtTokenUtil
import cz.upce.fei.janacek.lukas.exception.ResourceNotFoundException
import cz.upce.fei.janacek.lukas.model.User
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.InternalAuthenticationServiceException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class UserAuthenticationFilter(
    private val authManager: AuthenticationManager,
    private val jwtTokenUtil: JwtTokenUtil
): UsernamePasswordAuthenticationFilter() {

    override fun attemptAuthentication(req: HttpServletRequest, response: HttpServletResponse): Authentication {
        val credentials = ObjectMapper().registerKotlinModule().readValue(req.inputStream, LoginDto::class.java)
        val auth = UsernamePasswordAuthenticationToken(
            credentials.username,
            credentials.password,
        )
        try {
            return authManager.authenticate(auth)
        } catch (e: InternalAuthenticationServiceException) {
            logger.warn(e.message, e)
            throw ResourceNotFoundException("Cannot find user with username: ${credentials.username}")
        }
    }

    override fun successfulAuthentication(
        req: HttpServletRequest?,
        res: HttpServletResponse,
        chain: FilterChain?,
        auth: Authentication
    ) {
        val username = (auth.principal as User).username
        val token: String = jwtTokenUtil.generateToken(username)
        res.addHeader(SecurityConfiguration.AUTHORIZATION, token)
        res.addHeader("Access-Control-Expose-Headers", SecurityConfiguration.AUTHORIZATION)
        res.writer.write(token)
    }

    override fun unsuccessfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        failed: AuthenticationException
    ) {
        val error = BadCredentialsError()
        response.status = error.status
        response.contentType = "application/json"
        response.writer.append(error.toString())
    }

    private data class BadCredentialsError (
        val timestamp: Long = Date().time,
        val status: Int = 401,
        val message: String = "Username or password incorrect",
    ) {
        override fun toString(): String {
            return ObjectMapper().writeValueAsString(this)
        }
    }

}