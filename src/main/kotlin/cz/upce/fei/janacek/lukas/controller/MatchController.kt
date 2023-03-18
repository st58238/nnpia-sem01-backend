package cz.upce.fei.janacek.lukas.controller

import cz.upce.fei.janacek.lukas.dto.MatchExternalDto
import cz.upce.fei.janacek.lukas.dto.toEntity
import cz.upce.fei.janacek.lukas.dto.toExternalDto
import cz.upce.fei.janacek.lukas.service.MatchService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/matches/")
class MatchController (
    private val matchService: MatchService
) {

    @GetMapping("/{id}")
    fun getMatchById(
        @PathVariable
        id: Long
    ): ResponseEntity<MatchExternalDto> {
        val role = matchService.findById(id)
        return ResponseEntity.ok(role.toExternalDto())
    }

    @PostMapping("")
    fun create(
        @RequestBody
        @Validated
        matchDto: MatchExternalDto
    ): ResponseEntity<MatchExternalDto> {
        val result = matchService.create(matchDto.toEntity())
        return ResponseEntity<MatchExternalDto>(result.toExternalDto(), HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    fun modify(
        @PathVariable
        id: Long,
        @RequestBody
        @Validated
        matchDto: MatchExternalDto
    ): ResponseEntity<MatchExternalDto> {
        val result = matchService.modify(id, matchDto.toEntity(id))
        return ResponseEntity<MatchExternalDto>(result.toExternalDto(), HttpStatus.ACCEPTED)
    }

    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable
        id: Long
    ): ResponseEntity<MatchExternalDto> {
        val result = matchService.delete(id)
        return ResponseEntity<MatchExternalDto>(result.toExternalDto(), HttpStatus.FOUND)
    }

}