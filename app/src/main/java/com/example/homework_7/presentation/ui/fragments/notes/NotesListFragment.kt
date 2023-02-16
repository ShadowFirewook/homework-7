package com.example.homework_7.presentation.ui.fragments.notes

import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.homework_7.R
import com.example.homework_7.databinding.FragmentNotesListBinding
import com.example.homework_7.presentation.base.BaseFragment
import com.example.homework_7.presentation.ui.UIState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NotesListFragment : BaseFragment(R.layout.fragment_notes_list) {

    private val viewModel by viewModels<NotesViewModel>()
    private val notesAdapter by lazy {NotesAdapter()}
    private val binding by viewBinding(FragmentNotesListBinding::bind)

    override fun initialize() {
        setupNoteRecycler()
    }

    override fun setupListeners() {
        binding.fabAddNote.setOnClickListener{
            findNavController().navigate(R.id.action_notesListFragment_to_noteFragment)
        }
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

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getNotesState.collect {
                    when (it) {
                        is UIState.Empty -> {}
                        is UIState.Error -> {
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                        }
                        is UIState.Loading -> {
                            // TODO show progress bar
                        }
                        is UIState.Success -> {
                            notesAdapter?.submitList(it.data)
                        }
                    }
                }
            }
        }
    }

    private fun setupNoteRecycler() = with(binding.rvNotes) {
        adapter = notesAdapter
        layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)

    }

}