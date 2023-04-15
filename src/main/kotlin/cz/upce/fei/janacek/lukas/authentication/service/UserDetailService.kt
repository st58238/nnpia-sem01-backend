package cz.upce.fei.janacek.lukas.authentication.service

import cz.upce.fei.janacek.lukas.exception.ResourceNotFoundException
import cz.upce.fei.janacek.lukas.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserDetailService (
    private val userRepository: UserRepository
): UserDetailsService {

    @Transactional(readOnly = true)
    override fun loadUserByUsername(username: String?): UserDetails {
        username!!
        return userRepository.findByUsername(username) ?: throw ResourceNotFoundException()
    }
}