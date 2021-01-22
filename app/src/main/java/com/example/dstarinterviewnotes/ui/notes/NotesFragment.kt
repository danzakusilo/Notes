package com.example.dstarinterviewnotes.ui.notes

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.dstarinterviewnotes.R
import com.example.dstarinterviewnotes.data.source.local.database.NoteCategory
import com.example.dstarinterviewnotes.databinding.FragmentNotesBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class NotesFragment : Fragment(){

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


        adapter = NotesAdapter(requireActivity(), viewModel)
        binding.notesRv.layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.notesRv.adapter = adapter
        viewModel.getNotes()
        observeNotes()
        binding.newButton.setOnClickListener {
            view.findNavController().navigate(NotesFragmentDirections.actionToDetail(0, true))
        }

        setupSortSpinner()
        setupCategorySpinner()

        binding.searchEt.addTextChangedListener(
                object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                    override fun afterTextChanged(s: Editable?) {
                        if (s.isNullOrEmpty()) {
                            viewModel.getNotes()
                        } else {
                            adapter.submitList(viewModel.notes.value?.filter { it.title!!.toLowerCase(Locale.getDefault()).contains(binding.searchEt.text.toString()) })
                            adapter.notifyDataSetChanged()
                        }
                    }
                }
        )
    }

    private fun setupCategorySpinner() {
        ArrayAdapter(
                requireActivity(),
                android.R.layout.simple_spinner_item,
                NoteCategory.values()).also {
            it.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
            binding.categorySpinner.adapter = it
        }

        binding.categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
            ) {
                adapter.submitList(viewModel.notes.value?.filter { it.category == binding.categorySpinner.selectedItem as NoteCategory })
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    private fun setupSortSpinner(){
        ArrayAdapter.createFromResource(
            requireActivity(),
            R.array.sorting_patterns,
            android.R.layout.simple_spinner_item
        ).also {
            it.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
            binding.searchingPatternsSpinner.adapter = it
        }

        binding.searchingPatternsSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0 -> {
                        Toast.makeText(requireActivity(), "${binding.searchingPatternsSpinner.adapter.getItem(1)}", Toast.LENGTH_SHORT).show()
                        if (binding.categorySpinner.visibility == View.VISIBLE) {
                            binding.categorySpinner.visibility = View.GONE
                        }
                        viewModel.getNotes()

                    }
                    1 -> {
                        if (binding.categorySpinner.visibility == View.VISIBLE) {
                            binding.categorySpinner.visibility = View.GONE
                        }
                        adapter.submitList(viewModel.notes.value?.sortedBy { it.title?.toLowerCase(Locale.getDefault())})
                    }
                    2 -> {
                        if (binding.categorySpinner.visibility == View.GONE) {
                            binding.categorySpinner.visibility = View.VISIBLE
                        }
                        viewModel.notes.value?.sortedBy { it.category }
                    }
                }
                adapter.notifyDataSetChanged()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

    }

    private fun observeNotes(){
        viewModel.notes.observe(
            viewLifecycleOwner,
            { notes ->
                notes?.let {
                    adapter.submitList(notes)
                    Log.d("Note2","$notes")
                    adapter.notifyDataSetChanged()
                }
            }
        )
    }




}