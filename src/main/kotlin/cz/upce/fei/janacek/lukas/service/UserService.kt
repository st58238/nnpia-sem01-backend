package cz.upce.fei.janacek.lukas.service

import cz.upce.fei.janacek.lukas.exception.ResourceNotFoundException
import cz.upce.fei.janacek.lukas.model.User
import cz.upce.fei.janacek.lukas.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService (
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    @Transactional(readOnly = true)
    fun findById(id: Long): User {
        return userRepository.findByIdOrNull(id) ?: throw ResourceNotFoundException()
    }

    @Transactional(readOnly = true)
    fun findByUsername(username: String): User {
        return userRepository.findByUsername(username) ?: throw ResourceNotFoundException()
    }

    @Transactional
    fun create(user: User): User {
        val newPassword = passwordEncoder.encode(user.password)
        val hashedPasswordUser = user.cloneWithNewPassword(newPassword)
        return userRepository.save(hashedPasswordUser)
    }

    @Transactional
    fun modify(id: Long, newUser: User): User {
        val oldUser = userRepository.findByIdOrNull(id) ?: throw ResourceNotFoundException()
        val userToSave = User(
            oldUser.id,
            newUser.username,
            newUser.password,
            newUser.registeredDate,
            newUser.enabled,
            newUser.team,
            newUser.roles
        )
        return userRepository.save(userToSave)
    }

    @Transactional
    fun delete(id: Long): User {
        val user = userRepository.findByIdOrNull(id) ?: throw ResourceNotFoundException()
        userRepository.deleteById(id)
        return user
    }
}