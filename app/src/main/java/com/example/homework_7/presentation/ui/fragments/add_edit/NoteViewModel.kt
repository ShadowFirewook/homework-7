package com.example.homework_7.presentation.ui.fragments.add_edit

import com.example.homework_7.domain.model.Note
import com.example.homework_7.domain.usecases.CreateNoteUseCase
import com.example.homework_7.domain.usecases.EditNoteUseCase
import com.example.homework_7.presentation.base.BaseViewModel
import com.example.homework_7.presentation.ui.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val createNoteUseCase: CreateNoteUseCase,
    private val editNoteUseCase: EditNoteUseCase
): BaseViewModel() {

    private val _createNoteState = MutableStateFlow<UIState<Unit>>(UIState.Empty())
    val createNoteState = _createNoteState.asStateFlow()

    private val _editNoteState = MutableStateFlow<UIState<Unit>>(UIState.Empty())
    val editNoteState = _editNoteState.asStateFlow()

    fun createNote(note: Note){
        createNoteUseCase.createNote(note).collectFlow(_createNoteState)
    }

    fun editNote(note: Note){
        editNoteUseCase.editNote(note).collectFlow(_editNoteState)
    }

}