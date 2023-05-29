package cz.upce.fei.janacek.lukas.authentication.util

import cz.upce.fei.janacek.lukas.lib.JwtTokenUtil
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.Instant
import java.util.*
import javax.crypto.SecretKey

@SpringBootTest
internal class JwtTokenUtilTest (
    @Autowired key: SecretKey
) {
    companion object {const val USERNAME = "User00"}
    private val jwt = JwtTokenUtil(key)
    private val token = jwt.generateToken(USERNAME)

    @Test
    fun generateJwtToken() {
        assertTrue(token.isNotBlank())
        assertTrue(token.startsWith("ey"))
        assertTrue(token.count { it == '.' } == 2)
        assertTrue(token.matches(Regex("([a-zA-Z0-9]{20}\\.[a-zA-Z0-9]{44}\\.[a-zA-Z0-9_-]*)")))
    }

    @Test
    fun getUsernameFromJwtToken() {
        assertTrue(token.isNotBlank())
        assertEquals(USERNAME, jwt.getUsername(token))
    }

    @Test
    fun isJwtTokenValidSuccess() {
        assertTrue(token.isNotBlank())
        assertTrue(jwt.isTokenValid(token))
    }

    @Test
    fun isMalformedJwtTokenValidFail() {
        val token = token.removeRange(5, 6)
        assertTrue(token.isNotBlank())
        assertThrows(MalformedJwtException::class.java) {
            jwt.isTokenValid(token)
        }
    }

    @Test
    fun isJwtTokenInvalidException() {
        @Suppress("DEPRECATION")
        val token = jwt.generateToken(USERNAME, Date.from(Instant.now().minusSeconds(360000)))
        assertTrue(token.isNotBlank())
        assertTrue(token.startsWith("ey"))
        assertTrue(token.count { it == '.' } == 2)
        assertTrue(token.matches(Regex("([a-zA-Z0-9]{20}\\.[a-zA-Z0-9]{44}\\.[a-zA-Z0-9_-]*)")))
        assertThrows(ExpiredJwtException::class.java) {
            jwt.isTokenValid(token)
        }
    }
}