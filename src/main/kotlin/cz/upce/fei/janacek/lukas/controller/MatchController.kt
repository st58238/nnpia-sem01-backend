package cz.upce.fei.janacek.lukas.controller

import cz.upce.fei.janacek.lukas.dto.MatchExternalDto
import cz.upce.fei.janacek.lukas.dto.toEntity
import cz.upce.fei.janacek.lukas.dto.toExternalDto
import cz.upce.fei.janacek.lukas.service.MatchService
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/matches")
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

    @GetMapping("/page/{page}")
    fun getMatchPageByOffset(
        @PathVariable
        page: Long,
        @RequestParam
        size: Int,
        @RequestParam
        sort: String?,
        @RequestParam
        direction: String?
    ): ResponseEntity<Set<MatchExternalDto>> {
        val realSort = sort ?: "name"
        val ascDesc = direction?.uppercase()?.let { Sort.Direction.fromString(it) } ?: Sort.Direction.DESC
        val matches = matchService.findPage(page, size, Sort.by(ascDesc, realSort))
        val finalSet = matches.map { it.toExternalDto() }
        return ResponseEntity.ok(finalSet.toSet())
    }

    @GetMapping("/byUser")
    fun getUsersMatches(
        @RequestParam
        userId: Long
    ): ResponseEntity<Set<MatchExternalDto>> {
        val matches = matchService.findUsersMatches(userId)
        val matchDtos = matches.map { it.toExternalDto() }.toSet()
        return ResponseEntity.ok(matchDtos)
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