package com.example.homework_7.domain.usecases

import com.example.homework_7.domain.repository.NoteRepository
import javax.inject.Inject

class GetNotesUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {

    fun getNotes() = noteRepository.getNotes()

}