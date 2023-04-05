package cz.upce.fei.janacek.lukas.controller

import cz.upce.fei.janacek.lukas.dto.RoleExternalDto
import cz.upce.fei.janacek.lukas.dto.toEntity
import cz.upce.fei.janacek.lukas.dto.toExternalDto
import cz.upce.fei.janacek.lukas.service.RoleService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/roles/")
class RoleController (
    private val roleService: RoleService
) {

    @GetMapping("/{id}")
    fun getRoleById(
        @PathVariable
        id: Long
    ): ResponseEntity<RoleExternalDto> {
        val role = roleService.findById(id)
        return ResponseEntity.ok(role.toExternalDto())
    }

    @PostMapping("")
    fun create(
        @RequestBody
        @Validated
        roleDto: RoleExternalDto
    ): ResponseEntity<RoleExternalDto> {
        val result = roleService.create(roleDto.toEntity())
        return ResponseEntity<RoleExternalDto>(result.toExternalDto(), HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    fun modify(
        @PathVariable
        id: Long,
        @RequestBody
        @Validated
        roleDto: RoleExternalDto
    ): ResponseEntity<RoleExternalDto> {
        val result = roleService.modify(id, roleDto.toEntity(id))
        return ResponseEntity<RoleExternalDto>(result.toExternalDto(), HttpStatus.ACCEPTED)
    }

    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable
        id: Long
    ): ResponseEntity<RoleExternalDto> {
        val result = roleService.delete(id)
        return ResponseEntity<RoleExternalDto>(result.toExternalDto(), HttpStatus.FOUND)
    }
}
