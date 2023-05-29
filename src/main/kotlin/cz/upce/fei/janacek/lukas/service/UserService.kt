package cz.upce.fei.janacek.lukas.service

import cz.upce.fei.janacek.lukas.exception.ResourceNotFoundException
import cz.upce.fei.janacek.lukas.lib.JwtTokenUtil
import cz.upce.fei.janacek.lukas.model.User
import cz.upce.fei.janacek.lukas.repository.UserRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService (
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwt: JwtTokenUtil
) {

    val count: Long
        @Transactional
        get() = userRepository.count()

    @Transactional(readOnly = true)
    fun findById(id: Long): User {
        return userRepository.findByIdOrNull(id) ?: throw ResourceNotFoundException()
    }

    @Transactional(readOnly = true)
    fun findUserByToken(token: String): User {
        try {
            jwt.isTokenValid(token)
            val username = jwt.getUsername(token)
            return userRepository.findByUsername(username)!!
        } catch (_: Exception) {
            throw ResourceNotFoundException()
        }
    }

    @Transactional(readOnly = true)
    fun findPage(page: Long, size: Int, sort: Sort? = null): Set<User> {
        val users = if (sort == null)
            userRepository.findAll(PageRequest.of(page.toInt(), size))
        else
            userRepository.findAll(PageRequest.of(page.toInt(), size, sort))
        return users.toSet()
    }

    @Suppress("unused")
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

    @Transactional
    fun toggleEnabled(id: Long): User {
        val user = userRepository.findByIdOrNull(id) ?: throw ResourceNotFoundException()
        user.enabled = !user.enabled
        return userRepository.save(user)
    }
}