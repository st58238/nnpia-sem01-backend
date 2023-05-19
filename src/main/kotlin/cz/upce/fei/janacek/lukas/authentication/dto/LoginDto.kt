package cz.upce.fei.janacek.lukas.authentication.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class LoginDto (
    @field:NotBlank
    @field:Size(max = 255)
    val username: String,
    @field:NotBlank
    @field:Size(max = 255)
    val password: String
)