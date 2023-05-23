package cz.upce.fei.janacek.lukas.dto

import cz.upce.fei.janacek.lukas.model.Team
import cz.upce.fei.janacek.lukas.model.User
import cz.upce.fei.janacek.lukas.service.UserService
import java.time.format.DateTimeFormatter

data class UserExternalDto (
    val id: Long?,
    val username: String,
    val registeredDate: String,
    val enabled: Boolean,
    val team: Team?
)

fun UserExternalDto.toEntity(id: Long, userService: UserService): User {
    return userService.findById(id)
}

fun User.toExternalDto(): UserExternalDto {
    return UserExternalDto(id, username, registeredDate.format(DateTimeFormatter.ISO_DATE_TIME), enabled, team)
}