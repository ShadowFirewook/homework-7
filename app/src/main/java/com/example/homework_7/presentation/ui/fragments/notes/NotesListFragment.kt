package com.example.homework_7.presentation.ui.fragments.notes

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.homework_7.R
import com.example.homework_7.databinding.FragmentNotesListBinding
import com.example.homework_7.domain.model.Note
import com.example.homework_7.presentation.base.BaseFragment
import com.example.homework_7.presentation.ui.UIState
import com.example.homework_7.presentation.ui.fragments.add_edit.NoteFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NotesListFragment : BaseFragment(R.layout.fragment_notes_list) {

    private val viewModel by viewModels<NotesViewModel>()
    private val notesAdapter by lazy { NotesAdapter(this::onTaskClick, this::onTaskLongClick) }
    private val binding by viewBinding(FragmentNotesListBinding::bind)
    private var note: Note? = null

    override fun initialize() {
        getData()
        setupNoteRecycler()
        viewModel.getNotes()
    }

    private fun getData() {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.TIRAMISU) {
            note = arguments?.getSerializable(NoteFragment.EDIT_NOTE) as Note?
        }
    }

    override fun setupListeners() {
        binding.fabAddNote.setOnClickListener {
            findNavController().navigate(R.id.action_notesListFragment_to_noteFragment)
        }
    }

    private fun onTaskClick(note: Note) {
        val bundle = Bundle()
        bundle.putSerializable(NOTE, note)
        findNavController().navigate(R.id.action_notesListFragment_to_noteFragment, bundle)
    }

    private fun onTaskLongClick(note: Note) {
            val alertDialog = AlertDialog.Builder(requireContext())
            alertDialog.setTitle("Удалить?")
            alertDialog.setPositiveButton("Да") { dialog, _ ->
                viewModel.deleteNote(note)
                dialog.dismiss()

            }
            alertDialog.setNegativeButton("Нет") { dialog, _ ->
                dialog.dismiss()
            }
            alertDialog.create().show()
}

    override fun setupRequests() {
        viewModel.getNotes()
    }

    override fun setupSubscribers() {
        viewModel.getNotesState.collectState(
            onLoading = {
                binding.progress.isVisible = true
            },
            onError = {
                binding.progress.isVisible = false
                Toast.makeText(requireContext(),it,Toast.LENGTH_LONG).show()
            },
            onSuccess = {
                binding.progress.isVisible = false
                notesAdapter.submitList(it)
            }
        )

        viewModel.deleteNoteState.collectState(
            onLoading = {
                binding.progress.isVisible = true
            },
            onError = {
                binding.progress.isVisible = false
                Toast.makeText(requireContext(),it,Toast.LENGTH_LONG).show()
            },
            onSuccess = {
                viewModel.getNotes()
            }
        )


        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getNotesState.collect {
                    when (it) {
                        is UIState.Empty -> {}
                        is UIState.Error -> {
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                        }
                        is UIState.Loading -> {
                            binding.progress.isVisible = true
                        }
                        is UIState.Success -> {
                            notesAdapter.submitList(it.data)
                        }
                    }
                }
            }
        }


    }

    private fun setupNoteRecycler() = with(binding.rvNotes) {
        adapter = notesAdapter
        layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
    }

    companion object{
        const val NOTE = "note"
    }
}