package com.example.homework_7.data.mappers

import com.example.homework_7.data.model.NoteEntity
import com.example.homework_7.domain.model.Note

fun NoteEntity.toNote() = Note(
    id,
    title,
    description,
    createdAt
)

fun Note.toNoteEntity() = NoteEntity(
    id,
    title,
    description,
    createdAt
)