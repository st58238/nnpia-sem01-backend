package cz.upce.fei.janacek.lukas.repository

import cz.upce.fei.janacek.lukas.model.Match
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface MatchRepository: PagingAndSortingRepository<Match, Long>, JpaRepository<Match, Long> {

    @Suppress("SqlResolve")
    @Query(
        "SELECT DISTINCT match_id, description, name, team_one_id, team_two_id " +
                "FROM match " +
                "JOIN match_team_one_players ON match_id = match_team_one_players.match_match_id " +
                "JOIN match_team_two_players ON match_id = match_team_two_players.match_match_id " +
                "JOIN users u on match_team_one_players.team_one_players_user_id = u.user_id OR match_team_two_players.team_two_players_user_id = u.user_id " +
                "WHERE user_id = ?1",
        nativeQuery = true
    )
    fun findMatchesOfUser(userId: Long): Set<Match>
}