package cz.upce.fei.janacek.lukas.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class ResourceNotFoundException: RuntimeException {

    constructor(): super()
    constructor(message: String): super(message)

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFound() {}
}
