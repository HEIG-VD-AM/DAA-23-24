/**
 * Authors: Alexis Martins and Pablo Saez
 * Date: 05.12.2023
 *
 * @brief:
 * DatabaseNotes is an abstract class that extends RoomDatabase and represents the main access point
 * for the underlying SQLite database. It defines the entities (Note and Schedule), version number,
 * and type converters. The class also includes a companion object for getting the database instance
 * using the Singleton pattern. It initializes the database with sample data if it's empty.
 */

package com.example.daa_lab04.room

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlin.concurrent.thread
import com.example.daa_lab04.models.Note
import com.example.daa_lab04.models.Schedule

@Database(entities = [Note::class, Schedule::class], version = 1, exportSchema = true)
@TypeConverters(Converters::class)
abstract class DatabaseNotes : RoomDatabase() {

    abstract fun noteDao(): DAO

    companion object {
        private var INSTANCE: DatabaseNotes? = null

        fun getDatabase(context: Context) : DatabaseNotes {
            return INSTANCE?: synchronized(this) {
                INSTANCE = Room.databaseBuilder(context.applicationContext,
                    DatabaseNotes::class.java, "NotesDatabase.db")
                    .addCallback(DatabaseCallback())
                    .build()
                INSTANCE!!
            }
        }

        private class DatabaseCallback : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    if (database.noteDao().getCount().value == 0L) {
                        thread { populate(database) }
                    }
                }
            }
        }

        fun populate(database: DatabaseNotes) {
            repeat(5) {
                val schedule = Note.generateRandomSchedule()
                val note = Note.generateRandomNote()
                val id = database.noteDao().insert(note)
                schedule?.ownerId = id
                schedule?.let { database.noteDao().insert(it) }
            }
        }
    }
}