package com.example.dstarinterviewnotes.ui.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.dstarinterviewnotes.R
import com.example.dstarinterviewnotes.data.source.local.database.NoteCategory
import com.example.dstarinterviewnotes.data.source.local.database.entities.NoteEntity
import com.example.dstarinterviewnotes.databinding.FragmentNotesBinding
import com.example.dstarinterviewnotes.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotesFragment : Fragment() {

    private lateinit var binding : FragmentNotesBinding
    private lateinit var adapter: NotesAdapter
    private val viewModel : NotesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = NotesAdapter(requireActivity())
        binding.notesRv.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.notesRv.adapter = adapter
        val testList = listOf<NoteEntity>(
            NoteEntity(
                1,
                "TestTitleLOOOOOOOOONG",
                "TestContentLOOOOOOOOOOOoooooooooooooooooooooooooooojjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjoooooooooooooooooooooooooooTestContentLOOOOOOOOOOOoooooooooooooooooooooooooooojjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjoooooooooooooooooooooooooooTestContentLOOOOOOOOOOOoooooooooooooooooooooooooooojjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjooooooooooooooooooooooooooojjjjjjjjjjjjoNG",
                NoteCategory.DEFAULT.toString(),
                System.currentTimeMillis()
            ),
            NoteEntity(
                1,
                "TestTitleLOOOOOOOOONG",
                "TestContentLOOOOOOOOOOONG",
                NoteCategory.DEFAULT.toString(),
                System.currentTimeMillis()
            ),
            NoteEntity(
                1,
                "TestTitleLOOOOOOOOONG",
                "TestContentLOOOOOOOOOOONG",
                NoteCategory.DEFAULT.toString(),
                System.currentTimeMillis()
            ),
            NoteEntity(
                1,
                "TestTitleLOOOOOOOOONG",
                "TestContentLOOOOOOOOOOONG",
                NoteCategory.DEFAULT.toString(),
                System.currentTimeMillis()
            ),)
        adapter.submitList(testList)
    }

}