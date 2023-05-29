package cz.upce.fei.janacek.lukas.repository

import cz.upce.fei.janacek.lukas.model.Team
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface TeamRepository: PagingAndSortingRepository<Team, Long>, JpaRepository<Team, Long>