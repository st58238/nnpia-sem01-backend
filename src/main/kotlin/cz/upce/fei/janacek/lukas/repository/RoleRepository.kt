package cz.upce.fei.janacek.lukas.repository

import cz.upce.fei.janacek.lukas.model.Role
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface RoleRepository: PagingAndSortingRepository<Role, Long>, CrudRepository<Role, Long>