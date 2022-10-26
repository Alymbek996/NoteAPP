package com.geektech.noteapp.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.noteapp.domain.model.Note
import com.geektech.noteapp.R
import com.geektech.noteapp.databinding.FragmentFirstBinding
import com.geektech.noteapp.databinding.FragmentSecondBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteFragment : Fragment() {



        private lateinit var binding:  FragmentSecondBinding
        private var note: Note?=null

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
        ): View {
            binding = FragmentSecondBinding.inflate(inflater,container,false)
            return binding.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            note = arguments?.getSerializable("note") as Note?

            note?.let {
                binding.edTitle.setText(it.title)
                binding.EdText.setText(it.text)
            }
            binding.btnSave.setOnClickListener{
                save()
            }
        }

        private fun save() {
            val text = binding.EdText.text.toString().trim()
            val title = binding.edTitle.text.toString().trim()
            val bundle = Bundle()

            if (note == null) {
                note = Note(0,title,text)
            }else {
                note?.text = text
                note?.title = title
            }
            bundle.putSerializable("note", note)
            parentFragmentManager.setFragmentResult("note", bundle)
            findNavController().navigateUp()
        }
    }