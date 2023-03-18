package cz.upce.fei.janacek.lukas.dto

import cz.upce.fei.janacek.lukas.model.Team
import cz.upce.fei.janacek.lukas.model.Tournament
import java.time.LocalDateTime

class TournamentExternalDto (
    val id: Long,
    val name: String,
    val description: String,
    val startDateTime: LocalDateTime,
    val endDateTime: LocalDateTime,
    val participants: Set<Team>?
)

fun TournamentExternalDto.toEntity(): Tournament {
    return Tournament(id, name, description, startDateTime, endDateTime, participants)
}

fun Tournament.toExternalDto(): Tournament {
    return Tournament(id, name, description, startDateTime, endDateTime, participants)
}