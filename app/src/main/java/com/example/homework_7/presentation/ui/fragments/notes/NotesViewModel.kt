package com.example.homework_7.presentation.ui.fragments.notes

import com.example.homework_7.domain.model.Note
import com.example.homework_7.domain.usecases.DeleteNoteUseCase
import com.example.homework_7.domain.usecases.GetNotesUseCase
import com.example.homework_7.presentation.base.BaseViewModel
import com.example.homework_7.presentation.ui.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val getNotesUseCase: com.example.homework_7.domain.usecases.GetNotesUseCase,
    private val deleteNoteUseCase: com.example.homework_7.domain.usecases.DeleteNoteUseCase
): BaseViewModel() {

    private val _getNotesState = MutableStateFlow<UIState<List<com.example.homework_7.domain.model.Note>>>(UIState.Empty())
    val getNotesState = _getNotesState.asStateFlow()

    private val _deleteNoteState = MutableStateFlow<UIState<Unit>>(UIState.Empty())
    val deleteNoteState = _deleteNoteState.asStateFlow()

    fun getNotes(){
        getNotesUseCase.getNotes().collectFlow(_getNotesState)
    }

    fun deleteNote(note: com.example.homework_7.domain.model.Note){
        deleteNoteUseCase.deleteNote(note).collectFlow(_deleteNoteState)
    }

}