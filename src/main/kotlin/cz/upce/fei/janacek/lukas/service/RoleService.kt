package cz.upce.fei.janacek.lukas.service

import cz.upce.fei.janacek.lukas.exception.ResourceNotFoundException
import cz.upce.fei.janacek.lukas.model.Role
import cz.upce.fei.janacek.lukas.repository.RoleRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RoleService (
    private val roleRepository: RoleRepository
) {

    @Transactional(readOnly = true)
    fun findById(id: Long): Role {
        return roleRepository.findByIdOrNull(id) ?: throw ResourceNotFoundException()
    }

    @Transactional(readOnly = true)
    fun findPage(page: Long, size: Int): Set<Role> {
        val roles = roleRepository.findAll(PageRequest.of(page.toInt(), size))
        return roles.toSet()
    }

    @Transactional
    fun create(role: Role): Role {
        return roleRepository.save(role)
    }

    @Transactional
    fun modify(id: Long, newRole: Role): Role {
        val role = roleRepository.findByIdOrNull(id) ?: throw ResourceNotFoundException()
        val roleToSave = Role(
            role.id,
            newRole.name,
            newRole.description,
            newRole.assignees
        )
        return roleRepository.save(roleToSave)
    }

    @Transactional
    fun delete(id: Long): Role {
        val role = roleRepository.findByIdOrNull(id) ?: throw ResourceNotFoundException()
        roleRepository.deleteById(id)
        return role
    }
}
