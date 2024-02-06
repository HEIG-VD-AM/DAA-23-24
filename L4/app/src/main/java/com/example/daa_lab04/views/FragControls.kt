/**
 * Authors: Alexis Martins and Pablo Saez
 * Date: 05.12.2023
 *
 * @brief:
 * FragControls is a Fragment class that provides controls for generating new notes and deleting
 * all existing notes. It uses the NotesViewModel to observe the total number of notes and update
 * the UI accordingly. The fragment includes buttons for generating a new note and deleting all notes,
 * as well as a TextView to display the total count of notes.
 */

package com.example.daa_lab04.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.daa_lab04.R
import com.example.daa_lab04.MyApp
import com.example.daa_lab04.viewModels.NotesViewModel
import com.example.daa_lab04.viewModels.NotesViewModelFactory

class FragControls : Fragment() {

    // ViewModel initialization using viewModels delegate
    private val myViewModel: NotesViewModel by viewModels {
        NotesViewModelFactory((requireActivity().application as MyApp).repository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Find the UI elements by their IDs
        val generateButton = view.findViewById<Button>(R.id.btn_generate)
        val deleteButton = view.findViewById<Button>(R.id.btn_delete)
        val counterTextView = view.findViewById<TextView>(R.id.counter)

        // Observer to update the TextView with the total number of notes
        myViewModel.countNotes.observe(viewLifecycleOwner) { count ->
            counterTextView.text = count.toString()
        }

        // Set click listeners for the buttons
        generateButton.setOnClickListener {
            myViewModel.generateANote()
        }

        deleteButton.setOnClickListener {
            myViewModel.deleteAllNote()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frag_controls, container, false)
    }
}
