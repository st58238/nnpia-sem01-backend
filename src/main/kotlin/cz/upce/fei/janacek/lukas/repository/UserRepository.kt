package cz.upce.fei.janacek.lukas.repository

import cz.upce.fei.janacek.lukas.model.User
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: PagingAndSortingRepository<User, Long>, CrudRepository<User, Long> {
    fun findByUsername(username: String): User?
}