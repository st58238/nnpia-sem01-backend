package cz.upce.fei.janacek.lukas.controller

import cz.upce.fei.janacek.lukas.dto.TeamExternalDto
import cz.upce.fei.janacek.lukas.dto.toEntity
import cz.upce.fei.janacek.lukas.dto.toExternalDto
import cz.upce.fei.janacek.lukas.service.TeamService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/teams/")
class TeamController (
    private val teamService: TeamService
) {

    @GetMapping("/{id}")
    fun getTeamById(
        @PathVariable
        id: Long
    ): ResponseEntity<TeamExternalDto> {
        val team = teamService.findById(id)
        return ResponseEntity.ok(team.toExternalDto())
    }

    @GetMapping("/page/{page}")
    fun getTeamPageByOffset(
        @PathVariable
        page: Long,
        @RequestParam
        size: Int
    ): ResponseEntity<Set<TeamExternalDto>> {
        val teams = teamService.findPage(page, size)
        val finalSet = teams.map { it.toExternalDto() }.toSet()
        return ResponseEntity.ok(finalSet)
    }

    @PostMapping("")
    fun create(
        @RequestBody
        @Validated
        teamDto: TeamExternalDto
    ): ResponseEntity<TeamExternalDto> {
        val result = teamService.create(teamDto.toEntity())
        return ResponseEntity<TeamExternalDto>(result.toExternalDto(), HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    fun modify(
        @PathVariable
        id: Long,
        @RequestBody
        @Validated
        teamDto: TeamExternalDto
    ): ResponseEntity<TeamExternalDto> {
        val result = teamService.modify(id, teamDto.toEntity(id))
        return ResponseEntity<TeamExternalDto>(result.toExternalDto(), HttpStatus.ACCEPTED)
    }

    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable
        id: Long
    ): ResponseEntity<TeamExternalDto> {
        val result = teamService.delete(id)
        return ResponseEntity<TeamExternalDto>(result.toExternalDto(), HttpStatus.FOUND)
    }
}
