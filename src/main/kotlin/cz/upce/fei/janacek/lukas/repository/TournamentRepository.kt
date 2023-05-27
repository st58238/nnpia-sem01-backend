package cz.upce.fei.janacek.lukas.repository

import cz.upce.fei.janacek.lukas.model.Tournament
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface TournamentRepository: PagingAndSortingRepository<Tournament, Long>, JpaRepository<Tournament, Long>