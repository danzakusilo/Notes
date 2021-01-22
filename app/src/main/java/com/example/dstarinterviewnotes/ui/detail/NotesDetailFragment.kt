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
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    private val args : NotesDetailFragmentArgs by navArgs()
    private val viewModel : DetailViewModel by viewModels()
    private lateinit var binding : FragmentNoteDetailBinding

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
        if (!args.isNew){
            viewModel.getNote(args.noteId)
            observeNote()
        }
        binding.confirmButton.setOnClickListener {
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
            }else if (!args.isNew && args.noteId != 0){
                viewModel.editTitle(args.noteId, binding.titleEt.text.toString())
                viewModel.editContent(args.noteId, binding.titleEt.text.toString())
            }
            findNavController().navigateUp()
        }

        binding.addImageButton.setOnClickListener{
            showImageSelectDialogue(requireActivity())
        }

        binding.detailLayout.viewTreeObserver.addOnGlobalLayoutListener {
            val rect = Rect()
            binding.detailLayout.getWindowVisibleDisplayFrame(rect)
            val screenHeight = binding.detailLayout.rootView.height
            val keyPadHeight = screenHeight - rect.bottom
            //val navbar : BottomNavigationView = requireActivity().findViewById(R.id.nav_view)
            if (keyPadHeight > screenHeight * 0.15){
                binding.addImageButton.visibility = View.VISIBLE
                binding.confirmButton.visibility = View.VISIBLE
                //navbar.visibility = View.GONE
            }else{
                binding.addImageButton.visibility = View.GONE
                binding.confirmButton.visibility = View.GONE
                //navbar.visibility = View.VISIBLE
            }
        }

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

    private fun showImageSelectDialogue(context: Context){
        val options = arrayOf(getString(R.string.take_photo),getString(R.string.select_from_gallery),getString(R.string.cancel))
        val dialogueBuilder = AlertDialog.Builder(context)
        dialogueBuilder
                .setTitle(getString(R.string.picture_source))
                .setItems(options, object : DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, item: Int) {
                when(options[item]){
                    getString(R.string.take_photo) -> {
                        val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        startActivityForResult(takePicture, CAMERA_REQUEST_CODE)
                    }
                    getString(R.string.select_from_gallery) ->{
                        val pickPhoto = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                        startActivityForResult(pickPhoto, 1)
                    }
                    getString(R.string.cancel) -> {
                        dialog!!.dismiss()
                    }
                }
            }
        })
                .show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_CANCELED){
            when(requestCode){
                0 -> {
                    if (resultCode == RESULT_OK && data != null){
                        val image : Bitmap? = data.extras!!.get("data") as Bitmap?
                        Log.d("resultCode","RESULT_OK AND uri == $image"  )
                        //val source = ImageDecoder.createSource(requireActivity().contentResolver, imageUri!!)
                        //val bitmap = ImageDecoder.decodeBitmap(source)
                        binding.noteImage.setImageBitmap(image)
                    }
                }
                1 -> {
                    if (resultCode == RESULT_OK && data != null){
                        val imageUri : Uri? = data.data
                        val cursor: Cursor?
                        val filePathColumn = arrayOf(MediaStore.Images.Media._ID)
                        val columnIndex : Int
                        cursor = requireActivity().contentResolver.query(imageUri!!, filePathColumn, null, null, null)
                        if (cursor != null){
                            cursor.moveToFirst()
                            columnIndex = cursor.getColumnIndex(filePathColumn[0])
                            val picturePath = cursor.getString(columnIndex)
                            binding.noteImage.setImageBitmap(BitmapFactory.decodeFile(picturePath))
                            cursor.close()
                            }
                        }
                    }
                }
            }else
                Log.d("CANCELED","CANCELED")
        }
    }


