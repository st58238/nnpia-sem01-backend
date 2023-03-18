package cz.upce.fei.janacek.lukas.service

import cz.upce.fei.janacek.lukas.model.Tournament
import cz.upce.fei.janacek.lukas.repository.TournamentRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TournamentService (
    private val tournamentRepository: TournamentRepository
) {

    @Transactional(readOnly = true)
    fun findById(id: Long): Tournament {
        return tournamentRepository.findByIdOrNull(id) ?: throw ResourceNotFoundException()
    }

    @Transactional
    fun create(tournament: Tournament): Tournament {
        return tournamentRepository.save(tournament)
    }

    @Transactional
    fun modify(id: Long, newTournament: Tournament): Tournament {
        val oldTournament = tournamentRepository.findByIdOrNull(id) ?: throw ResourceNotFoundException()
        val tournamentToSave = Tournament(
            oldTournament.id,
            newTournament.name,
            newTournament.description,
            newTournament.startDateTime,
            newTournament.endDateTime,
            newTournament.participants
        )
        return tournamentRepository.save(tournamentToSave)
    }

    @Transactional
    fun delete(id: Long): Tournament {
        val tournament = tournamentRepository.findByIdOrNull(id) ?: throw ResourceNotFoundException()
        tournamentRepository.deleteById(id)
        return tournament
    }
}
