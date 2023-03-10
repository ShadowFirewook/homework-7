package com.example.homework_7.data.repository

import com.example.homework_7.data.base.BaseRepository
import com.example.homework_7.data.local.NoteDao
import com.example.homework_7.data.mappers.toNote
import com.example.homework_7.data.mappers.toNoteEntity
import com.example.homework_7.domain.model.Note
import com.example.homework_7.domain.repository.NoteRepository
import com.example.homework_7.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao
): BaseRepository(),NoteRepository {

    override fun createNote(note: Note): Flow<Resource<Unit>> = doRequest {
        noteDao.createNote(note.toNoteEntity())
    }

    override fun editNote(note: Note): Flow<Resource<Unit>> = doRequest {
        noteDao.editNote(note.toNoteEntity())
    }


    override fun deleteNote(note: Note): Flow<Resource<Unit>> = doRequest {
        noteDao.deleteNote(note.toNoteEntity())
    }


    override fun getNotes(): Flow<Resource<List<Note>>> = doRequest {
        noteDao.getNotes().map { it.toNote() }
    }

}