package cz.upce.fei.janacek.lukas.lib

import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import java.nio.ByteBuffer
import java.util.*
import javax.crypto.SecretKey

@Component
class Key {

    @Value("\${user.authorization.key.part.1}")
    private lateinit var keyPart1: String

    @Value("\${user.authorization.key.part.2}")
    private lateinit var keyPart2: String

    @Value("\${user.authorization.key.part.3}")
    private lateinit var keyPart3: String

    @Value("\${user.authorization.key.part.4}")
    private lateinit var keyPart4: String

    @Bean
    fun secretKey(): SecretKey {
        val parts = listOf(
            UUID.fromString(keyPart1),
            UUID.fromString(keyPart2),
            UUID.fromString(keyPart3),
            UUID.fromString(keyPart4)
        )
        val bytes = mutableListOf<Byte>()
        parts.forEach { bytes.addAll(it.asBytes().toTypedArray()) }
        return Keys.hmacShaKeyFor(bytes.toTypedArray().toByteArray())
    }


    private fun UUID.asBytes(): ByteArray {
        val b = ByteBuffer.wrap(ByteArray(16))
        b.putLong(mostSignificantBits)
        b.putLong(leastSignificantBits)
        return b.array()
    }
}