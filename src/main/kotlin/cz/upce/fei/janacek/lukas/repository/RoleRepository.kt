package cz.upce.fei.janacek.lukas.repository

import cz.upce.fei.janacek.lukas.model.Role
import org.springframework.data.repository.PagingAndSortingRepository

interface RoleRepository: PagingAndSortingRepository<Role, Long> {

}