package cz.upce.fei.janacek.lukas.service

import cz.upce.fei.janacek.lukas.exception.ResourceNotFoundException
import cz.upce.fei.janacek.lukas.model.Team
import cz.upce.fei.janacek.lukas.repository.TeamRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TeamService (
    private val teamRepository: TeamRepository
) {
    @Transactional(readOnly = true)
    fun findById(id: Long): Team {
        return teamRepository.findByIdOrNull(id) ?: throw ResourceNotFoundException()
    }

    @Transactional
    fun create(team: Team): Team {
        return teamRepository.save(team)
    }

    @Transactional
    fun modify(id: Long, newTeam: Team): Team {
        val oldTeam = teamRepository.findByIdOrNull(id) ?: throw ResourceNotFoundException()
        val teamToSave = Team(
            oldTeam.id,
            newTeam.name,
            newTeam.description,
            newTeam.leader,
            newTeam.mentor,
            newTeam.members
        )
        return teamRepository.save(teamToSave)
    }

    @Transactional
    fun delete(id: Long): Team {
        val team = teamRepository.findByIdOrNull(id) ?: throw ResourceNotFoundException()
        teamRepository.deleteById(id)
        return team
    }
}
