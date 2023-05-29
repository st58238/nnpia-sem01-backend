package cz.upce.fei.janacek.lukas.controller

import cz.upce.fei.janacek.lukas.dto.*
import cz.upce.fei.janacek.lukas.service.UserService
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController (
    private val userService: UserService
) {

    @Secured("ROLE_USER_LIST")
    @GetMapping("/{id}")
    fun getUserById(
        @PathVariable
        id: Long
    ): ResponseEntity<UserExternalDto> {
        val user = userService.findById(id)
        return ResponseEntity.ok(user.toExternalDto())
    }

    @GetMapping("/userByToken")
    fun getUserByToken(
        @RequestParam
        token: String
    ): ResponseEntity<UserExternalDto> {
        val user = userService.findUserByToken(token)
        return ResponseEntity.ok(user.toExternalDto())
    }

    @Secured("ROLE_USER_LIST")
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
        val realSort = sort ?: "registeredDate"
        val ascDesc = direction?.uppercase()?.let { Sort.Direction.fromString(it) } ?: Sort.Direction.DESC
        val users = userService.findPage(page, size, Sort.by(ascDesc, realSort))
        val finalSet = users.map { it.toExternalDto() }.toSet()
        return ResponseEntity.ok(finalSet)
    }

    @Secured("ROLE_USER_LIST")
    @GetMapping("/count")
    fun countOfUsers(): ResponseEntity<Long> {
        return ResponseEntity.ok(userService.count)
    }

    @Secured("ROLE_USER_CREATE")
    @PostMapping("", "/create")
    fun create(
        @RequestBody
        @Validated
        userDto: UserRegistrationDto
    ): ResponseEntity<UserExternalDto> {
        val result = userService.create(userDto.toEntity())
        return ResponseEntity<UserExternalDto>(result.toExternalDto(), HttpStatus.CREATED)
    }

    @Secured("ROLE_USER_EDIT")
    @PutMapping("/{id}")
    fun modify(
        @PathVariable
        id: Long,
        @RequestBody
        @Validated
        userDto: UserExternalDto
    ): ResponseEntity<UserExternalDto> {
        val result = userService.modify(id, userDto.toEntity(id, userService))
        return ResponseEntity<UserExternalDto>(result.toExternalDto(), HttpStatus.ACCEPTED)
    }

    @Secured("ROLE_USER_EDIT")
    @PatchMapping("/{id}")
    fun patch(
        @PathVariable
        id: Long,
        @RequestBody
        @Validated
        userDto: UserExternalDto
    ): ResponseEntity<UserExternalDto> {
        val result = userService.modify(id, userDto.toEntity(id, userService))
        return ResponseEntity<UserExternalDto>(result.toExternalDto(), HttpStatus.ACCEPTED)
    }

    @Secured("ROLE_USER_ENABLE")
    @PatchMapping("/enable/{id}")
    fun toggleEnabled(
        @PathVariable
        id: Long
    ): ResponseEntity<UserExternalDto> {
        val result = userService.toggleEnabled(id)
        return ResponseEntity<UserExternalDto>(result.toExternalDto(), HttpStatus.ACCEPTED)
    }

    @Secured("ROLE_USER_DELETE")
    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable
        id: Long
    ): ResponseEntity<UserExternalDto> {
        val result = userService.delete(id)
        return ResponseEntity<UserExternalDto>(result.toExternalDto(), HttpStatus.FOUND)
    }
}
