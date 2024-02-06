/**
 * Authors: Alexis Martins and Pablo Saez
 * Date: 05.12.2023
 *
 * @brief:
 * MyApp is a custom Application class that initializes the Room database and provides
 * a lazy-initialized Repository instance for the application.
 */

package com.example.daa_lab04

import android.app.Application
import com.example.daa_lab04.room.DatabaseNotes
import com.example.daa_lab04.room.Repository

class MyApp: Application() {
    val repository by lazy {
        val database = DatabaseNotes.getDatabase(this)
        Repository(database.noteDao())
    }
}