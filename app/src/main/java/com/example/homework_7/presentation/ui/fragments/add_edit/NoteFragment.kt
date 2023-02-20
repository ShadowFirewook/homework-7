package com.example.homework_7.presentation.ui.fragments.add_edit

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.homework_7.R
import com.example.homework_7.databinding.FragmentNoteBinding
import com.example.homework_7.domain.model.Note
import com.example.homework_7.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteFragment : BaseFragment(R.layout.fragment_note) {

    private val binding by viewBinding(FragmentNoteBinding::bind)
    private val viewModel by viewModels<NoteViewModel>()
    private var note: com.example.homework_7.domain.model.Note? = null

    override fun initialize() {

    }

    override fun setupListeners() {
        binding.btnSave.setOnClickListener{
            viewModel.createNote(
                com.example.homework_7.domain.model.Note(
                    title = binding.tvNoteName.text.toString(),
                    description = binding.tvNoteName.text.toString(),
                    createdAt = System.currentTimeMillis()
                )
            )
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
    }

}