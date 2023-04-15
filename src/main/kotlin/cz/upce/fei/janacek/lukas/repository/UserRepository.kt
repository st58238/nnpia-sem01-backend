package cz.upce.fei.janacek.lukas.repository

import cz.upce.fei.janacek.lukas.model.User
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository()
interface UserRepository: PagingAndSortingRepository<User, Long> {
    fun findByUsername(username: String): User?
}