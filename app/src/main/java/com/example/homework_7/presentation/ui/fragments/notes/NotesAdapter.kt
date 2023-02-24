package com.example.homework_7.presentation.ui.fragments.notes

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.homework_7.databinding.ItemNoteBinding
import com.example.homework_7.domain.model.Note
import java.util.*

class NotesAdapter(
    private val onTaskClick:(Note) -> Unit,
    private val onTaskLongClick:(Note) -> Unit
): ListAdapter<Note, NotesAdapter.NotesViewHolder>(
    NoteDiffUtil()
) {

    inner class NotesViewHolder(private val binding: ItemNoteBinding): ViewHolder(binding.root){
        fun bind(item: Note){
            binding.tvNoteName.text = item.title
            binding.tvNoteDescription.text = item.description
         /*   val s: Date = Date()
            val f :DateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            val t = f.format(s)
            val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
            val ttext = timeFormat.format(s)*/

            binding.tvCreatedAt.text = item.createdAt.toString()

            itemView.setOnClickListener{
                onTaskClick(item)
            }

            itemView.setOnLongClickListener{
                onTaskLongClick(item)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return  NotesViewHolder(ItemNoteBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private class NoteDiffUtil: DiffUtil.ItemCallback<Note>(){
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }

}