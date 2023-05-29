package cz.upce.fei.janacek.lukas.repository

import cz.upce.fei.janacek.lukas.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: PagingAndSortingRepository<User, Long>, JpaRepository<User, Long> {
    fun findByUsername(username: String): User?
}