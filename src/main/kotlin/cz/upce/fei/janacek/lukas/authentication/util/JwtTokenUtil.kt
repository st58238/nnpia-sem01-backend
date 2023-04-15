package cz.upce.fei.janacek.lukas.authentication.util

import cz.upce.fei.janacek.lukas.authentication.config.SecurityConfiguration
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtTokenUtil (private val key: SecretKey) {

    @Suppress("UnnecessaryVariable")
    fun generateToken(username: String): String {
        val token = Jwts.builder()
            .setSubject(username)
            .setExpiration(Date(System.currentTimeMillis() + SecurityConfiguration.EXPIRATION_IN_MILLIS))
            .signWith(key, SignatureAlgorithm.HS512).compact()
        return token
    }

    private fun getClaims(token: String): Claims {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).body
    }

    fun getUsername(token: String): String {
        return getClaims(token).subject
    }

    fun isTokenValid(token: String): Boolean {
        val claims = getClaims(token)
        val expirationDate = claims.expiration
        val now = Date(System.currentTimeMillis())
        return now.before(expirationDate)
    }
}