package cz.upce.fei.janacek.lukas.dto

import cz.upce.fei.janacek.lukas.model.Match
import cz.upce.fei.janacek.lukas.model.Team
import cz.upce.fei.janacek.lukas.model.User

class MatchExternalDto (
    val id: Long?,
    val name: String,
    val description: String?,
    val teamOne: Team,
    val teamTwo: Team,
    val teamOnePlayers: Set<User>,
    val teamTwoPlayers: Set<User>
)

fun MatchExternalDto.toEntity(id: Long? = null): Match {
    return Match(id ?: 0, name, description, teamOne, teamTwo, teamOnePlayers, teamTwoPlayers)
}

fun Match.toExternalDto(): MatchExternalDto {
    return MatchExternalDto(id, name, description, teamOne, teamTwo, teamOnePlayers, teamTwoPlayers)
}