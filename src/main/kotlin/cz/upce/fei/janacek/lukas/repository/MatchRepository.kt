package cz.upce.fei.janacek.lukas.repository

import cz.upce.fei.janacek.lukas.model.Match
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface MatchRepository: PagingAndSortingRepository<Match, Long>