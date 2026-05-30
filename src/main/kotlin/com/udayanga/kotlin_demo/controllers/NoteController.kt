package com.udayanga.kotlin_demo.controllers

import com.udayanga.kotlin_demo.controllers.NoteController.Response
import com.udayanga.kotlin_demo.database.model.Note
import com.udayanga.kotlin_demo.database.repository.NoteRepository
import org.bson.types.ObjectId
import org.springframework.web.bind.annotation.*
import java.time.Instant

@RestController
@RequestMapping("/notes")
class NoteController(
    val repository: NoteRepository
) {
    data class Request(
        val id: String?,
        val title: String,
        val content: String
    )

    data class Response(
        val id: String,
        val title: String,
        val content: String,
        val createdAt: Instant
    )

    @PostMapping
    fun saveNote(
        @RequestBody body: Request
    ): Response {
        val note = repository.save(
            Note(
                id = body.id?.let { ObjectId(it) } ?: ObjectId.get(),
                title = body.title,
                content = body.content,
                createdAt = Instant.now(),
                ownerId = ObjectId()
            )
        )
        return note.toResponse()

    }

    @GetMapping
    fun findByOwnerId(
        @RequestParam(required = true) ownerId: String
    ): List<Response> {
        return repository.findByOwnerId(ObjectId(ownerId)).map {
            it.toResponse()
        }
    }
}

private fun Note.toResponse(): Response {
    return Response(
        id = id.toHexString(),
        title = title,
        content = content,
        createdAt = createdAt
    )
}