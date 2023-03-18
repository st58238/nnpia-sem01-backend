package cz.upce.fei.janacek.lukas.repository

import cz.upce.fei.janacek.lukas.model.Team
import org.springframework.data.repository.PagingAndSortingRepository

interface TeamRepository: PagingAndSortingRepository<Team, Long> {

}
