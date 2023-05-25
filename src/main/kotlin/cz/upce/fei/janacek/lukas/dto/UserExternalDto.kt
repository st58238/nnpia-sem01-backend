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
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserExternalDto

        if (id != other.id) return false
        if (username != other.username) return false
        if (registeredDate != other.registeredDate) return false
        if (enabled != other.enabled) return false
        if (team != other.team) return false

        return true
    }
    
    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + username.hashCode()
        result = 31 * result + registeredDate.hashCode()
        result = 31 * result + enabled.hashCode()
        result = 31 * result + (team?.hashCode() ?: 0)
        return result
    }
}

@Suppress("UnusedReceiverParameter")
fun UserExternalDto.toEntity(id: Long, userService: UserService): User {
    return userService.findById(id)
}

fun User.toExternalDto(): UserExternalDto {
    return UserExternalDto(id, username, registeredDate.format(DateTimeFormatter.ISO_DATE_TIME), enabled, team)
}