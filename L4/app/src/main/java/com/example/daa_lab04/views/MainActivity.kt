/**
 * Authors: Alexis Martins and Pablo Saez
 * Date: 05.12.2023
 *
 * @brief:
 * MainActivity serves as the main entry point for the application. It initializes the application's UI
 * by setting the content view to the activity_main layout. The activity also handles options menu creation
 * and item selection. The menu provides options to delete all notes, generate a new note, and sort notes
 * by either ETA (estimated time of arrival) or creation date. These actions are triggered by corresponding
 * menu item selections, which invoke appropriate methods from the NotesViewModel.
 */
package com.example.daa_lab04.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import com.example.daa_lab04.MyApp
import com.example.daa_lab04.R
import com.example.daa_lab04.viewModels.NotesViewModel
import com.example.daa_lab04.viewModels.NotesViewModelFactory
import com.example.daa_lab04.viewModels.SortType
import com.example.daa_lab04.SortPreferencesManager

class MainActivity : AppCompatActivity() {

    private val myViewModel: NotesViewModel by viewModels {
        NotesViewModelFactory((application as MyApp).repository)
    }

    // Lazy initialization for SortPreferencesManager
    private val sortPreferencesManager by lazy {
        SortPreferencesManager(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Select the initial sort type based on saved preferences
        myViewModel.selectSortType(sortPreferencesManager.getSortType())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu layout
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.deleteAll -> {
                // Delete all notes when the "Delete All" menu item is selected
                myViewModel.deleteAllNote()
                true
            }
            R.id.generate -> {
                // Generate a new note when the "Generate" menu item is selected
                myViewModel.generateANote()
                true
            }
            R.id.eta -> {
                // Select the ETA sort type and save it in preferences
                myViewModel.selectSortType(SortType.ETA)
                sortPreferencesManager.saveSortType(SortType.ETA)
                true
            }
            R.id.creation -> {
                // Select the Creation sort type and save it in preferences
                myViewModel.selectSortType(SortType.CREATED)
                sortPreferencesManager.saveSortType(SortType.CREATED)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

