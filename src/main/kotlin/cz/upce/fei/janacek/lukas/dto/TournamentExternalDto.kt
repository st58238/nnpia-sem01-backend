package cz.upce.fei.janacek.lukas.dto

import cz.upce.fei.janacek.lukas.model.Match
import cz.upce.fei.janacek.lukas.model.Team
import cz.upce.fei.janacek.lukas.model.Tournament
import java.time.LocalDateTime

data class TournamentExternalDto (
    val id: Long?,
    val name: String,
    val description: String,
    val startDateTime: LocalDateTime,
    val endDateTime: LocalDateTime,
    val matches: Set<Match>,
    val participants: Set<Team>?
)

fun TournamentExternalDto.toEntity(id: Long? = null): Tournament {
    return Tournament(id ?: 0, name, description, startDateTime, endDateTime, matches, participants)
}

fun Tournament.toExternalDto(): TournamentExternalDto {
    return TournamentExternalDto(id, name, description, startDateTime, endDateTime, matches, participants)
}