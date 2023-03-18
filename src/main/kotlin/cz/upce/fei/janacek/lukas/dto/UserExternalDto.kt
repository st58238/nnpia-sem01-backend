package cz.upce.fei.janacek.lukas.dto

import cz.upce.fei.janacek.lukas.model.Role
import cz.upce.fei.janacek.lukas.model.Team
import cz.upce.fei.janacek.lukas.model.User
import java.time.LocalDateTime

class UserExternalDto (
    val id: Long?,
    val username: String,
    val password: String,
    val registeredDate: LocalDateTime,
    val enabled: Boolean,
    val team: Team,
    val roles: Set<Role>
)

fun UserExternalDto.toEntity(id: Long? = null): User {
    return User(id ?: 0, username, password, registeredDate, enabled, team, roles)
}

fun User.toExternalDto(): UserExternalDto {
    return UserExternalDto(id, username, password, registeredDate, enabled, team, roles)
}