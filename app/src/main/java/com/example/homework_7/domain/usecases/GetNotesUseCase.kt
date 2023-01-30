package com.example.homework_7.domain.usecases

import com.example.homework_7.domain.repository.NoteRepository

class GetNotesUseCase(
    private val noteRepository: NoteRepository
) {

    fun getNotes() = noteRepository.getNotes()

}