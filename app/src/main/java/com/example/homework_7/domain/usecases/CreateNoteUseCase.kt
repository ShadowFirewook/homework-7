package com.example.homework_7.domain.usecases

import com.example.homework_7.domain.model.Note
import com.example.homework_7.domain.repository.NoteRepository
import javax.inject.Inject

class CreateNoteUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {

    fun createNote(note: Note) = noteRepository.createNote(note)
}