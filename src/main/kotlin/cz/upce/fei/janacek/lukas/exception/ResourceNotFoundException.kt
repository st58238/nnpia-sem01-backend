package cz.upce.fei.janacek.lukas.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.server.ResponseStatusException

@ResponseStatus(HttpStatus.NOT_FOUND)
class ResourceNotFoundException: ResponseStatusException {

    constructor(status: HttpStatus): super(status)
    constructor(status: HttpStatus, message: String): super(status, message)

    constructor(): this(HttpStatus.NOT_FOUND)

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFound() {}
}
