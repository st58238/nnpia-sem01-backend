package cz.upce.fei.janacek.lukas.service

import cz.upce.fei.janacek.lukas.exception.ResourceNotFoundException
import cz.upce.fei.janacek.lukas.model.Match
import cz.upce.fei.janacek.lukas.repository.MatchRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MatchService (
    private val matchRepository: MatchRepository
) {

    @Transactional(readOnly = true)
    fun findById(id: Long): Match {
        return matchRepository.findByIdOrNull(id) ?: throw ResourceNotFoundException()
    }

    @Transactional
    fun create(match: Match): Match {
        return matchRepository.save(match)
    }

    @Transactional
    fun modify(id: Long, newMatch: Match): Match {
        val oldMatch = matchRepository.findByIdOrNull(id) ?: throw ResourceNotFoundException()
        val matchToSave = Match(
            oldMatch.id,
            newMatch.name,
            newMatch.description,
            newMatch.teamOne,
            newMatch.teamTwo,
            newMatch.teamOnePlayers,
            newMatch.teamTwoPlayers
        )
        return matchRepository.save(matchToSave)
    }

    @Transactional
    fun delete(id: Long): Match {
        val match = matchRepository.findByIdOrNull(id) ?: throw ResourceNotFoundException()
        matchRepository.deleteById(id)
        return match
    }
}
