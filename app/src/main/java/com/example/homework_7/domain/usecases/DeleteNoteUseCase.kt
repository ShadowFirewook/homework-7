package com.example.homework_7.domain.usecases

import com.example.homework_7.domain.model.Note
import com.example.homework_7.domain.repository.NoteRepository

class DeleteNoteUseCase(
    private val noteRepository: NoteRepository
) {

    fun deleteNote(note: Note) = noteRepository.deleteNote(note)
}