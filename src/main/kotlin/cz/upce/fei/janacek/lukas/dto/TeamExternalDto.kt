package cz.upce.fei.janacek.lukas.dto

import cz.upce.fei.janacek.lukas.model.Team
import cz.upce.fei.janacek.lukas.model.User

data class TeamExternalDto (
    val id: Long?,
    val name: String,
    val description: String?,
    val leader: User?,
    val mentor: User?,
    val members: Set<User>
)

fun TeamExternalDto.toEntity(id: Long? = null): Team {
    return Team(id ?: 0, name, description, leader, mentor, members)
}

fun Team.toExternalDto(): TeamExternalDto {
    return TeamExternalDto(id, name, description, leader, mentor, members)
}