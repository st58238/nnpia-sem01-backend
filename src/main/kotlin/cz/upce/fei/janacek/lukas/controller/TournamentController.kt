package cz.upce.fei.janacek.lukas.controller

import cz.upce.fei.janacek.lukas.dto.TournamentExternalDto
import cz.upce.fei.janacek.lukas.dto.toEntity
import cz.upce.fei.janacek.lukas.dto.toExternalDto
import cz.upce.fei.janacek.lukas.service.TournamentService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/tournaments")
class TournamentController (
    private val tournamentService: TournamentService
) {

    @GetMapping("/page/{page}")
    fun getTournamentPageByOffset(
        @PathVariable
        page: Long,
        @RequestParam
        size: Int
    ): ResponseEntity<Set<TournamentExternalDto>> {
        val tournaments = tournamentService.findPage(page, size)
        val finalSet = tournaments.map { it.toExternalDto() }.toSet()
        return ResponseEntity.ok(finalSet)
    }

    @GetMapping("/byUser")
    fun getUsersMatches(
        @RequestParam
        userId: Long
    ): ResponseEntity<Set<TournamentExternalDto>> {
        val tournaments = tournamentService.findUsersTournaments(userId)
        val tournamentDtos = tournaments.map { it.toExternalDto() }.toSet()
        return ResponseEntity.ok(tournamentDtos)
    }

    @GetMapping("/{id}")
    fun getTournamentById(
        @PathVariable
        id: Long
    ): ResponseEntity<TournamentExternalDto> {
        val tournament = tournamentService.findById(id)
        return ResponseEntity.ok(tournament.toExternalDto())
    }

    @PostMapping("")
    fun create(
        @RequestBody
        @Validated
        tournamentDto: TournamentExternalDto
    ): ResponseEntity<TournamentExternalDto> {
        val result = tournamentService.create(tournamentDto.toEntity())
        return ResponseEntity<TournamentExternalDto>(result.toExternalDto(), HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    fun modify(
        @PathVariable
        id: Long,
        @RequestBody
        @Validated
        tournamentDto: TournamentExternalDto
    ): ResponseEntity<TournamentExternalDto> {
        val result = tournamentService.modify(id, tournamentDto.toEntity(id))
        return ResponseEntity<TournamentExternalDto>(result.toExternalDto(), HttpStatus.ACCEPTED)
    }

    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable
        id: Long
    ): ResponseEntity<TournamentExternalDto> {
        val result = tournamentService.delete(id)
        return ResponseEntity<TournamentExternalDto>(result.toExternalDto(), HttpStatus.FOUND)
    }
}
