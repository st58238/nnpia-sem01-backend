package cz.upce.fei.janacek.lukas.controller

import cz.upce.fei.janacek.lukas.dto.*
import cz.upce.fei.janacek.lukas.service.UserService
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController (
    private val userService: UserService
) {

    @GetMapping("/{id}")
    fun getUserById(
        @PathVariable
        id: Long
    ): ResponseEntity<UserExternalDto> {
        val user = userService.findById(id)
        return ResponseEntity.ok(user.toExternalDto())
    }

    @GetMapping("/page/{page}")
    fun getUserPageByOffsetWithSort(
        @PathVariable
        page: Long,
        @RequestParam
        size: Int,
        @RequestParam
        sort: String?,
        @RequestParam
        direction: String?
    ): ResponseEntity<Set<UserExternalDto>> {
        val ascDesc = direction?.uppercase()?.let { Sort.Direction.fromString(it) }
        val users = userService.findPage(page, size, ascDesc?.let { Sort.by(it, sort) })
        val finalSet = users.map { it.toExternalDto() }.toSet()
        return ResponseEntity.ok(finalSet)
    }

    @PostMapping("", "/create")
    fun create(
        @RequestBody
        @Validated
        userDto: UserRegistrationDto
    ): ResponseEntity<UserExternalDto> {
        val result = userService.create(userDto.toEntity())
        return ResponseEntity<UserExternalDto>(result.toExternalDto(), HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    fun modify(
        @PathVariable
        id: Long,
        @RequestBody
        @Validated
        userDto: UserExternalDto
    ): ResponseEntity<UserExternalDto> {
        val result = userService.modify(id, userDto.toEntity(id))
        return ResponseEntity<UserExternalDto>(result.toExternalDto(), HttpStatus.ACCEPTED)
    }

    @PatchMapping("/{id}")
    fun patch(
        @PathVariable
        id: Long,
        @RequestBody
        @Validated
        userDto: UserExternalDto
    ): ResponseEntity<UserExternalDto> {
        val result = userService.modify(id, userDto.toEntity(id))
        return ResponseEntity<UserExternalDto>(result.toExternalDto(), HttpStatus.ACCEPTED)
    }

    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable
        id: Long
    ): ResponseEntity<UserExternalDto> {
        val result = userService.delete(id)
        return ResponseEntity<UserExternalDto>(result.toExternalDto(), HttpStatus.FOUND)
    }
}
