/**
 * Authors: Alexis Martins and Pablo Saez
 * Date: 05.12.2023
 *
 * @brief:
 * Repository is a class that abstracts access to multiple data sources. It provides methods for
 * inserting a note and an optional schedule, deleting all notes, and observing the list of all notes
 * and the count of notes. The repository acts as a clean API for data access to the rest of the
 * application. It uses a background thread for performing database operations.
 */

package com.example.daa_lab04.room

import com.example.daa_lab04.models.Note
import com.example.daa_lab04.models.Schedule
import kotlin.concurrent.thread

class Repository(private val notesDao: DAO) {

    val allNotes = notesDao.getAllNotes()
    val countNotes = notesDao.getCount()

    fun insert(note: Note, schedule: Schedule?) {
        val id = notesDao.insert(note)
        print("ID: $id")
        if (schedule != null) {
            schedule.ownerId = id
            notesDao.insert(schedule)
        }
    }

    fun deleteAll() {
        thread {
            notesDao.deleteAll()
        }
    }
}