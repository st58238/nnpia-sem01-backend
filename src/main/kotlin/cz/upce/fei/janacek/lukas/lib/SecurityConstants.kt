/*package cz.upce.fei.janacek.lukas.authentication.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import java.nio.ByteBuffer
import java.util.*
import javax.annotation.PostConstruct

@Component
class SecurityConstants {

    @Value("\${user.authorization.key.part.1}")
    private lateinit var keyPart1: String

    @Value("\${user.authorization.key.part.2}")
    private lateinit var keyPart2: String

    @Value("\${user.authorization.key.part.3}")
    private lateinit var keyPart3: String

    @Value("\${user.authorization.key.part.4}")
    private lateinit var keyPart4: String

    private lateinit var SECRET_KEY_PARTS: List<UUID>

    private fun constructSecretKey(): Array<Byte> {
        val out = mutableListOf<Byte>()
        SECRET_KEY_PARTS.forEach { out.addAll(it.asBytes().toTypedArray()) }
        return out.toTypedArray()
    }

    @PostConstruct
    private fun init() {
        SECRET_KEY_PARTS = listOf(
            UUID.fromString(keyPart1),
            UUID.fromString(keyPart2),
            UUID.fromString(keyPart3),
            UUID.fromString(keyPart4)
        )
        SECRET_KEY_BYTES = constructSecretKey()
    }

    companion object {
        internal const val AUTHORIZATION = "Authorization"
        internal const val PREFIX = "Bearer "
        internal const val EXPIRATION_IN_MILLIS = 5 * 3_600_000
        internal lateinit var SECRET_KEY_BYTES: Array<Byte>
    }
}

private fun UUID.asBytes(): ByteArray {
    val b = ByteBuffer.wrap(ByteArray(16))
    b.putLong(mostSignificantBits)
    b.putLong(leastSignificantBits)
    return b.array()
}*/