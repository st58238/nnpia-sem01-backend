package cz.upce.fei.janacek.lukas.repository

import cz.upce.fei.janacek.lukas.model.Tournament
import org.springframework.data.repository.PagingAndSortingRepository

interface TournamentRepository: PagingAndSortingRepository<Tournament, Long> {

}