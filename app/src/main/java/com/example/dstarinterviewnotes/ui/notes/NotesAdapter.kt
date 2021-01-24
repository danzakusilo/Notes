package com.example.dstarinterviewnotes.ui.notes

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.dstarinterviewnotes.R
import com.example.dstarinterviewnotes.data.source.local.database.entities.NoteEntity
import com.example.dstarinterviewnotes.databinding.CardviewNoteItemBinding
import com.example.dstarinterviewnotes.utils.toDateTime
import java.time.*
import java.util.*

class NotesAdapter constructor(private val context : Context, private val viewModel : NotesViewModel)
    : ListAdapter<NoteEntity, NotesAdapter.NoteViewHolder>(NotesCallback()) {
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
        private val context : Context,
    ) : RecyclerView.ViewHolder(binding.root), PopupMenu.OnMenuItemClickListener{

        fun bind(note : NoteEntity){
            val action = NotesFragmentDirections.actionToDetail(note.id, false)
            binding.contentTv.text = note.content
            binding.titleTv.text = note.title
            binding.timestampTv.text = toDateTime(note.creationTime)
            binding.noteItemLayout.setOnClickListener {
                view -> view.findNavController().navigate(action)
            }
            binding.noteItemLayout.setOnLongClickListener{
                showPopupMenu(it)
                true
            }
            if (!note.imageURI.isNullOrBlank()) {
                Glide.with(context).load(note.imageURI).apply(RequestOptions.overrideOf(400)).into(binding.imagePreview)
            }
        }

        private fun showPopupMenu(view : View){
            val menu = PopupMenu(view.context, view)
            menu.inflate(R.menu.note_context_menu)
            menu.setOnMenuItemClickListener(this)
            menu.show()
        }
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                return when(item!!.itemId){
                    R.id.delete_menu_option ->{ showDeleteDialog(context, R.style.AlertDialogCustom)
                        true
                    }else -> false
                }
        }

        private fun showDeleteDialog(context: Context, themeId : Int){
            val dialog = AlertDialog.Builder(context, themeId)
            dialog.apply {
                setPositiveButton(R.string.delete
                ) { _, _ ->
                    viewModel.deleteNote(currentList[adapterPosition].id)
                    viewModel.getNotes()
                }
                setNegativeButton(R.string.cancel
                ){dialog, _, ->
                    dialog.dismiss()
                }
                dialog.setTitle(R.string.note_delete_confirmation)
            }
            dialog.show()
        }


    }
    class NotesCallback() :DiffUtil.ItemCallback<NoteEntity>(){
        override fun areItemsTheSame(oldItem: NoteEntity, newItem: NoteEntity): Boolean
            = oldItem == newItem

        override fun areContentsTheSame(oldItem: NoteEntity, newItem: NoteEntity): Boolean
            = oldItem == newItem

    }
}