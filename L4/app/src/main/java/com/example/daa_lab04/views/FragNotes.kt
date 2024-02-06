/**
 * Authors: Alexis Martins and Pablo Saez
 * Date: 05.12.2023
 *
 * @brief:
 * FragNotes is a Fragment class responsible for displaying a RecyclerView of notes. It uses the
 * NotesViewModel to observe changes in the underlying data and updates the UI accordingly. It also
 * provides a method for sorting the displayed notes based on the selected SortType.
 */

package com.example.daa_lab04.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.daa_lab04.MyApp
import com.example.daa_lab04.R
import com.example.daa_lab04.models.NoteAndSchedule
import com.example.daa_lab04.viewModels.NotesViewModel
import com.example.daa_lab04.viewModels.NotesViewModelFactory
import com.example.daa_lab04.viewModels.SortType

class FragNotes : Fragment() {

    private val viewModelFrag: NotesViewModel by activityViewModels {
        NotesViewModelFactory((requireActivity().application as MyApp).repository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recycler = view.findViewById<RecyclerView>(R.id.notes_recycler)
        val adapter = RecyclerAdapter()
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(context)

        viewModelFrag.types.observe(viewLifecycleOwner) {
            adapter.items = sortItems(adapter.items, it)
        }

        viewModelFrag.allNotes.observe(viewLifecycleOwner) {
            adapter.items  = viewModelFrag.types.value?.let { itemType -> sortItems(it, itemType) }!!
        }
    }
    fun sortItems(items: List<NoteAndSchedule>, type: SortType): List<NoteAndSchedule> {
        return when (type) {
            SortType.CREATED -> items.sortedBy { it.note.creationDate }
            SortType.ETA -> items.sortedWith(compareBy(nullsLast()) { it.schedule?.date })
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.frag_notes, container, false)

}