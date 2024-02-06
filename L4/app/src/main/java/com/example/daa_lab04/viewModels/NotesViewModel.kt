/**
 * Authors: Alexis Martins and Pablo Saez
 * Date: 05.12.2023
 *
 * @brief:
 * NotesViewModel is a ViewModel class that serves as the communication layer between the UI
 * (Fragments and Activities) and the data model (Repository). It provides LiveData objects for
 * observing the list of notes, the count of notes, and the selected sorting type. Users can select
 * the sorting type to apply to the list of notes, generate a new random note, and delete all notes.
 * The NotesViewModelFactory is used to create instances of the NotesViewModel with the required
 * dependencies.
 */

package com.example.daa_lab04.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.daa_lab04.room.Repository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.daa_lab04.models.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

enum class SortType {
    CREATED,
    ETA
}

class NotesViewModel(private val repository: Repository) : ViewModel() {

    // LiveData for all notes and count of notes
    val allNotes = repository.allNotes
    val countNotes = repository.countNotes

    // LiveData for the selected sort type
    private val _types = MutableLiveData(SortType.ETA)
    val types: LiveData<SortType> = _types

    // Function to select the sort type
    fun selectSortType(sort: SortType) {
        _types.value = sort
    }

    // Function to generate a random note
    fun generateANote() = viewModelScope.launch(Dispatchers.IO) {
        val schedule = Note.generateRandomSchedule()
        repository.insert(Note.generateRandomNote(), schedule)
    }

    // Function to delete all notes
    fun deleteAllNote() {
        repository.deleteAll()
    }
}

class NotesViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NotesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
