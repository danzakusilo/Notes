package com.example.dstarinterviewnotes.ui.notes

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dstarinterviewnotes.data.source.local.database.entities.NoteEntity
import com.example.dstarinterviewnotes.databinding.CardviewNoteItemBinding
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneId
import java.util.*

class NotesAdapter constructor(private val context : Context) : ListAdapter<NoteEntity, NotesAdapter.NoteViewHolder>(NotesCallback()) {
    val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CardviewNoteItemBinding.inflate(inflater, parent, false)

        return NoteViewHolder(binding, context)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = getItem(position)
        holder.bind(note)
    }


    inner class NoteViewHolder(
        private val binding : CardviewNoteItemBinding,
        private val context : Context
    ) : RecyclerView.ViewHolder(binding.root){


        fun bind(note : NoteEntity){
            val action = NotesFragmentDirections.actionToDetail(note.id)
            binding.contentTv.text = note.content
            binding.titleTv.text = note.title
            binding.timestampTv.text = format.format(note.creationTime)
            binding.noteItemLayout.setOnClickListener {
                view -> view.findNavController().navigate(action)
            }
        }
    }

    class NotesCallback() :DiffUtil.ItemCallback<NoteEntity>(){
        override fun areItemsTheSame(oldItem: NoteEntity, newItem: NoteEntity): Boolean
            = oldItem == newItem

        override fun areContentsTheSame(oldItem: NoteEntity, newItem: NoteEntity): Boolean
            = oldItem == newItem

    }

}