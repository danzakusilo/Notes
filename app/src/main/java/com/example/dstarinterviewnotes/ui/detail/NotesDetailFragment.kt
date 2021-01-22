package com.example.dstarinterviewnotes.ui.detail

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.ContentUris
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.graphics.Rect
import android.net.Uri
import android.opengl.Visibility
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.dstarinterviewnotes.R
import com.example.dstarinterviewnotes.data.source.local.database.NoteCategory
import com.example.dstarinterviewnotes.data.source.local.database.entities.NoteEntity
import com.example.dstarinterviewnotes.databinding.FragmentNoteDetailBinding
import com.example.dstarinterviewnotes.utils.viewBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_note_detail.view.*

@AndroidEntryPoint
class NotesDetailFragment : Fragment() {

    private val CAMERA_REQUEST_CODE = 0
    private val args: NotesDetailFragmentArgs by navArgs()
    private val viewModel: DetailViewModel by viewModels()
    private lateinit var binding: FragmentNoteDetailBinding
    private var actionMode: ActionMode? = null
    private val callback = object : ActionMode.Callback {
        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            requireActivity().menuInflater.inflate(R.menu.top_bar_menu, menu)
            return true
        }
        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            return false
        }
        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
            return when (item?.itemId) {
                R.id.confirm_button_appbar -> {
                    saveNote()
                    findNavController().navigateUp()
                    true
                }else -> false
            }
        }
        override fun onDestroyActionMode(mode: ActionMode?) {
            findNavController().navigateUp()
        }
    }


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNoteDetailBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!args.isNew) {
            viewModel.getNote(args.noteId)
            observeNote()
        }
        actionMode = requireActivity().startActionMode(callback)
        actionMode?.title = getString(R.string.detail_label)
        binding.detailLayout.viewTreeObserver.addOnGlobalLayoutListener {
            val rect = Rect()
            binding.detailLayout.getWindowVisibleDisplayFrame(rect)
            val screenHeight = binding.detailLayout.rootView.height
            val keyPadHeight = screenHeight - rect.bottom
            if (keyPadHeight > screenHeight * 0.15) {
                binding.addImageButton.visibility = View.VISIBLE
            } else {
                binding.addImageButton.visibility = View.GONE
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        actionMode?.finish()
    }

    private fun observeNote() {
        viewModel.note.observe(
                viewLifecycleOwner,
                {
                    binding.titleEt.setText(it.title)
                    binding.contentEt.setText(it.content)
                    //val imageSource = ImageDecoder.createSource(requireActivity().contentResolver, Uri.parse(it.imageURI))
                    // val bitmap = ImageDecoder.decodeBitmap(imageSource)
                    //binding.noteImage.setImageBitmap(bitmap)
                }
        )
    }
    private fun saveNote(){
        if (args.isNew) {
            viewModel.saveNote(
                    NoteEntity(
                            args.noteId,
                            binding.titleEt.text.toString(),
                            binding.contentEt.text.toString(),
                            NoteCategory.DEFAULT,
                            System.currentTimeMillis(),
                    )
            )
        } else if (!args.isNew && args.noteId != 0) {
            viewModel.editTitle(args.noteId, binding.titleEt.text.toString())
            viewModel.editContent(args.noteId, binding.contentEt.text.toString())
        }
    }

}



