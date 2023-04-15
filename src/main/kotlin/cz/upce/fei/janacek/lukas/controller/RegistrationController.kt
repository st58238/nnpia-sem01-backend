package cz.upce.fei.janacek.lukas.controller

import cz.upce.fei.janacek.lukas.dto.UserRegistrationDto
import cz.upce.fei.janacek.lukas.dto.toEntity
import cz.upce.fei.janacek.lukas.service.UserService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/register/")
class RegistrationController (
    private val userService: UserService
) {

    @PostMapping("")
    fun registerNewUser(
        @RequestBody
        @Validated
        userRegistrationDto: UserRegistrationDto
    ) {
        userService.create(userRegistrationDto.toEntity())
    }
}