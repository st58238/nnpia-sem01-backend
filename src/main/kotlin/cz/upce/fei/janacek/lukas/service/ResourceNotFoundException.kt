package cz.upce.fei.janacek.lukas.service

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class ResourceNotFoundException: RuntimeException() {

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFound() {}
}
