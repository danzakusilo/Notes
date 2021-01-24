package com.example.dstarinterviewnotes.ui.detail

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.*
import android.graphics.Rect
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
import com.example.dstarinterviewnotes.R
import com.example.dstarinterviewnotes.data.source.local.database.entities.NoteEntity
import com.example.dstarinterviewnotes.databinding.FragmentNoteDetailBinding
import com.example.dstarinterviewnotes.utils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NotesDetailFragment : Fragment() {

    private val GALLERY_REQUST_CODE = 100
    private val IMAGE_SIZE = 1000
    private val args: NotesDetailFragmentArgs by navArgs()
    private val viewModel: DetailViewModel by viewModels()
    private lateinit var binding: FragmentNoteDetailBinding
    private var actionMode: ActionMode? = null

    /*
    Эта переменная нужна чтобы временно хранить URI изображения, которое добавили в интерфейсе, но ещё не сохранили.
    Мне не очень нравится такой подход, но единственный другой подход, который я видел https://stackoverflow.com/a/42200812/14915818,
    как я понял, требует сделать от меня то же самое, только временно хранить не сам URI, а объект Bitmap, что не решает проблему "хранения состояния во фрагменте".
    Всё это не имеет значения, конечно, если мой подход к сохранению заметки, собирая объект из элементов UI фундаментально неправильный.
     */

    private var imageUri: String = ""

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
                }
                else -> false
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
            selectImageFromGallery()
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
                    if (!it.imageURI.isNullOrEmpty()) {
                        Glide.with(this).load(it.imageURI).apply(RequestOptions.overrideOf(1000)).into(binding.noteImage)
                        imageUri = it.imageURI
                    }
                }
        )
    }

    private fun saveNote() {
        if (args.isNew) {
            viewModel.saveNote(
                    NoteEntity(
                            args.noteId,
                            binding.titleEt.text.toString(),
                            binding.contentEt.text.toString(),
                            System.currentTimeMillis(),
                            imageUri
                    )
            )
        } else if (!args.isNew && args.noteId != 0) {
            viewModel.editTitle(args.noteId, binding.titleEt.text.toString())
            viewModel.editContent(args.noteId, binding.contentEt.text.toString())
            viewModel.editURI(args.noteId, imageUri)
        }
        hideKeyboard()
    }

    private fun showDeletePopup(view: View) {
        val menu = PopupMenu(view.context, view)
        menu.inflate(R.menu.note_context_menu)
        menu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.delete_menu_option -> {
                    showImageDeleteDialog(requireActivity(), R.style.AlertDialogCustom)
                    true
                }
                else -> false
            }
        }
        menu.show()
    }

    private fun showImageDeleteDialog(context: Context, themeID: Int) {
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


    private fun selectImageFromGallery() {
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
                    imageUri = uri.toString()
                }
            }
        }
    }
}



