package cz.upce.fei.janacek.lukas.dto

import cz.upce.fei.janacek.lukas.model.Role
import cz.upce.fei.janacek.lukas.model.Team
import cz.upce.fei.janacek.lukas.model.User
import java.time.LocalDateTime
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class UserRegistrationDto (
    @field:NotBlank
    @field:Size(max = 255)
    val username: String,
    @field:NotBlank
    @field:Size(max = 255)
    val password: String,
    val registeredDate: LocalDateTime = LocalDateTime.now(),
    val enabled: Boolean = true,
    val team: Team? = null,
    val roles: Set<Role> = mutableSetOf()
)

fun UserRegistrationDto.toEntity(): User {
    return User(0, username, password, registeredDate, enabled, team, roles)
}
