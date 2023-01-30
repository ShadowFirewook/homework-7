package com.example.homework_7.domain.repository

import com.example.homework_7.domain.model.Note


interface NoteRepository {


    fun createNote(note:Note )

    fun editNote(note:Note )

    fun deleteNote(note:Note )

    fun getNotes(): List<Note>

}