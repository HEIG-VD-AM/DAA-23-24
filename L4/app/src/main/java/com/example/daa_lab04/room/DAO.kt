/**
 * Authors: Alexis Martins and Pablo Saez
 * Date: 05.12.2023
 *
 * @brief:
 * DAO (Data Access Object) is an interface that defines the methods to interact with the Room database.
 * It includes methods for inserting a Note or Schedule, getting the count of notes, retrieving all notes
 * with schedules, and deleting all notes from the database.
 */

package com.example.daa_lab04.room;

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.daa_lab04.models.Note
import com.example.daa_lab04.models.NoteAndSchedule
import com.example.daa_lab04.models.Schedule

@Dao
interface DAO {
    @Insert
    fun insert(note: Note) : Long

    @Insert
    fun insert(schedule: Schedule) : Long

    @Query("SELECT COUNT(*) FROM Note")
    fun getCount() : LiveData<Long>

    @Query("SELECT * FROM Note")
    fun getAllNotes() : LiveData<List<NoteAndSchedule>>

    @Query("DELETE FROM Note")
    fun deleteAll()
}
