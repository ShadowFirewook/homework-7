package com.example.homework_7.domain.usecases

import com.example.homework_7.domain.model.Note
import com.example.homework_7.domain.repository.NoteRepository
import javax.inject.Inject

class EditNoteUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {

    fun editNote(note: Note) = noteRepository.editNote(note)

}