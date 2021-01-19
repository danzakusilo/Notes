package com.example.dstarinterviewnotes.ui.detail

import android.graphics.Rect
import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.dstarinterviewnotes.R
import com.example.dstarinterviewnotes.databinding.FragmentNoteDetailBinding
import com.example.dstarinterviewnotes.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotesDetailFragment : Fragment() {

    private val args : NotesDetailFragmentArgs by navArgs()

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

        binding.detailLayout.viewTreeObserver.addOnGlobalLayoutListener {
            val rect = Rect()
            binding.detailLayout.getWindowVisibleDisplayFrame(rect)
            val screenHeight = binding.detailLayout.rootView.height
            val keyPadHeight = screenHeight - rect.bottom
            if (keyPadHeight > screenHeight * 0.15){
                binding.addImageButton.visibility = View.VISIBLE
                binding.confirmButton.visibility = View.VISIBLE
            }else{
                binding.addImageButton.visibility = View.GONE
                binding.confirmButton.visibility = View.GONE
            }
        }

    }



}