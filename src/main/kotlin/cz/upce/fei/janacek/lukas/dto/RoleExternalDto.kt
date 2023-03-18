package cz.upce.fei.janacek.lukas.dto

import cz.upce.fei.janacek.lukas.model.Role
import cz.upce.fei.janacek.lukas.model.User

class RoleExternalDto (
    val id: Long,
    val name: String,
    val description: String?,
    val assignees: Set<User>
)

fun RoleExternalDto.toEntity(): Role {
    return Role(id, name, description, assignees)
}

fun Role.toExternalDto(): RoleExternalDto {
    return RoleExternalDto(id, name, description, assignees)
}