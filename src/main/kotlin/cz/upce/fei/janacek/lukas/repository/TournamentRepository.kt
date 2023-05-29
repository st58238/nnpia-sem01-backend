package cz.upce.fei.janacek.lukas.repository

import cz.upce.fei.janacek.lukas.model.Tournament
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface TournamentRepository: PagingAndSortingRepository<Tournament, Long>, JpaRepository<Tournament, Long> {

    @Suppress("SqlResolve")
    @Query(
        "SELECT DISTINCT tournament_id, t.description, end_date_time, t.name, start_date_time " +
                "FROM tournament t " +
                "JOIN tournament_participants tp on t.tournament_id = tp.tournament_tournament_id " +
                "JOIN team te on tp.participants_team_id = te.team_id " +
                "JOIN team_members tm on te.team_id = tm.team_team_id " +
                "JOIN users u on tm.members_user_id = u.user_id " +
                "WHERE user_id = ?1",
        nativeQuery = true
    )
    fun findTournamentsOfUser(userId: Long): Set<Tournament>
}