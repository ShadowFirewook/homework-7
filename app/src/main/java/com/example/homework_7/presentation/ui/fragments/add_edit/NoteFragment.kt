package com.example.homework_7.presentation.ui.fragments.add_edit

import android.annotation.SuppressLint
import android.os.Build
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.homework_7.R
import com.example.homework_7.databinding.FragmentNoteBinding
import com.example.homework_7.domain.model.Note
import com.example.homework_7.presentation.base.BaseFragment
import com.example.homework_7.presentation.ui.fragments.notes.NotesListFragment
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class NoteFragment : BaseFragment(R.layout.fragment_note) {

    private val binding by viewBinding(FragmentNoteBinding::bind)
    private val viewModel by viewModels<NoteViewModel>()
    private var note: Note? = null
    private var isEmptyData = true

    @SuppressLint("SetTextI18n")
    override fun initialize() {
        getData()

        isEmptyData = note == null

        if (isEmptyData){
            binding.btnSave.text = "save"
        }else
            binding.btnSave.text = "edit"

        binding.tvNoteName.setText(note?.title)
        binding.tvNoteDescription.setText(note?.description)


    }

    private fun getData(){

        if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.TIRAMISU) {
            note = arguments?.getSerializable(NotesListFragment.NOTE) as Note?
        }

    }

    override fun setupListeners() {
        binding.btnSave.setOnClickListener {
            if (isEmptyData){
                viewModel.createNote(
                    Note(
                        title = binding.tvNoteName.text.toString(),
                        description = binding.tvNoteDescription.text.toString(),
                        createdAt = Calendar.getInstance().timeInMillis
                    )
                )
            } else {
                viewModel.editNote(
                    note as Note
                )
            }

        }
    }

    override fun setupSubscribers() {
        viewModel.createNoteState.collectState(
            onLoading = {

            },
            onError = {

            },
            onSuccess = {
                findNavController().navigateUp()
            }
        )

        viewModel.editNoteState.collectState(
            onLoading = {

            },
            onError = {

            },
            onSuccess = {
                viewModel.editNote(note as Note)
                note!!.title = binding.tvNoteName.text.toString()
                note!!.description = binding.tvNoteDescription.text.toString()
                findNavController().navigate(R.id.notesListFragment, bundleOf(EDIT_NOTE to note))
            }
        )

    }

    companion object{
        const val EDIT_NOTE = "edit note"
    }

}