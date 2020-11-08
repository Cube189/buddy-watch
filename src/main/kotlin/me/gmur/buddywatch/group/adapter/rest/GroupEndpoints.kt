package me.gmur.buddywatch.group.adapter.rest

import me.gmur.buddywatch.auth.domain.model.Token
import me.gmur.buddywatch.group.domain.model.Group
import me.gmur.buddywatch.group.domain.model.GroupUrl
import me.gmur.buddywatch.group.domain.service.GroupService
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/group")
class GroupEndpoints(private val service: GroupService) {

    @PostMapping
    fun create(group: Group): ResponseEntity<GroupUrl> {
        return ResponseEntity(service.create(group).url, HttpStatus.CREATED)
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun assign(group: Group, @RequestHeader(HttpHeaders.AUTHORIZATION) auth: String) {
        val token = Token(auth)

        service.assign(token, group)
    }
}
