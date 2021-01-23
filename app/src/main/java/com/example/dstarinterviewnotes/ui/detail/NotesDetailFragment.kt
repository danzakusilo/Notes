package com.example.dstarinterviewnotes.ui.detail

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.app.Dialog
import android.content.*
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.*
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.dstarinterviewnotes.R
import com.example.dstarinterviewnotes.data.source.local.database.NoteCategory
import com.example.dstarinterviewnotes.data.source.local.database.entities.NoteEntity
import com.example.dstarinterviewnotes.databinding.FragmentNoteDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.NonCancellable.cancel
import java.io.File


@AndroidEntryPoint
class NotesDetailFragment : Fragment() {

    private val GALLERY_REQUST_CODE = 100
    private val IMAGE_SIZE = 1000
    private val args: NotesDetailFragmentArgs by navArgs()
    private val viewModel: DetailViewModel by viewModels()
    private lateinit var binding: FragmentNoteDetailBinding
    private var actionMode: ActionMode? = null
    private var imageUri : String = ""
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
        binding.addImageButton.setOnClickListener {
            selectImage()
        }
        binding.noteImage.setOnLongClickListener {
            showDeletePopup(it)
            true
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
                if (!it.imageURI.isNullOrEmpty()){
                    Glide.with(this).load(it.imageURI).apply(RequestOptions.overrideOf(1000 )).into(binding.noteImage)
                    imageUri = it.imageURI
                }
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
                    imageUri
                )
            )
        } else if (!args.isNew && args.noteId != 0) {
            viewModel.editTitle(args.noteId, binding.titleEt.text.toString())
            viewModel.editContent(args.noteId, binding.contentEt.text.toString())
            viewModel.editURI(args.noteId, imageUri)
        }
    }

    private fun showDeletePopup(view: View){
        val menu = PopupMenu(view.context, view)
        menu.inflate(R.menu.note_context_menu)
        menu.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.delete_menu_option -> {
                    showImageDeleteDialog(requireActivity(), R.style.AlertDialogCustom)
                    true
                }
                else -> false
            }
        }
        menu.show()
    }
    private fun showImageDeleteDialog(context: Context, themeID : Int){
        val dialog = AlertDialog.Builder(context, themeID)
        dialog.apply {
            setPositiveButton(R.string.delete) { _, _ ->
                binding.noteImage.setImageResource(android.R.color.transparent)
                viewModel.editURI(args.noteId, "")
            }
            setNegativeButton(R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }
            setTitle(R.string.image_delete_confirmation)
        }
        dialog.show()
    }



    private fun selectImage(){
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, GALLERY_REQUST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != RESULT_CANCELED) {
            when (requestCode) {
                GALLERY_REQUST_CODE -> if (resultCode == RESULT_OK && data != null) {
                    Log.d("PICTURE", "onActivityResult: not cancelled")
                    Toast.makeText(requireActivity(), "RESULT OK", Toast.LENGTH_SHORT).show()
                    val uri = data.data
                    Glide.with(this).load(uri).apply(RequestOptions.overrideOf(IMAGE_SIZE )).into(binding.noteImage)
                    imageUri = uri.toString()
                }
            }
        }
    }
}



