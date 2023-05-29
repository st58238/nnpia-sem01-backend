package cz.upce.fei.janacek.lukas.lib

import cz.upce.fei.janacek.lukas.configuration.SecurityConfiguration
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.jetbrains.annotations.TestOnly
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtTokenUtil (
    private val key: SecretKey
) {

    @TestOnly
    @Deprecated(
        "Used for testing purposes only, but can be used to override expiration time (5h default)",
        replaceWith = ReplaceWith("generateToken(username: String): String")
    )
    internal fun generateToken(username: String, expiration: Date): String {
        return Jwts.builder()
            .setSubject(username)
            .setExpiration(expiration)
            .signWith(key, SignatureAlgorithm.HS512).compact()
    }

    @Suppress("DEPRECATION")
    fun generateToken(username: String): String {
        return generateToken(username, Date(System.currentTimeMillis() + SecurityConfiguration.EXPIRATION_IN_MILLIS))
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