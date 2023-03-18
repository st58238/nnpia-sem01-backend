package cz.upce.fei.janacek.lukas.dto

import cz.upce.fei.janacek.lukas.model.Team
import cz.upce.fei.janacek.lukas.model.Tournament
import java.time.LocalDateTime

class TournamentExternalDto (
    val id: Long?,
    val name: String,
    val description: String,
    val startDateTime: LocalDateTime,
    val endDateTime: LocalDateTime,
    val participants: Set<Team>?
)

fun TournamentExternalDto.toEntity(id: Long? = null): Tournament {
    return Tournament(id ?: 0, name, description, startDateTime, endDateTime, participants)
}

fun Tournament.toExternalDto(): TournamentExternalDto {
    return TournamentExternalDto(id, name, description, startDateTime, endDateTime, participants)
}