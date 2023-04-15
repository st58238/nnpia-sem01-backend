package cz.upce.fei.janacek.lukas.authentication.dto

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class LoginDto (
    @field:NotBlank
    @field:Size(max = 255)
    val username: String,
    @field:NotBlank
    @field:Size(max = 255)
    val password: String
)