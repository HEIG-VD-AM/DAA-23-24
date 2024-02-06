/**
 * Authors: Alexis Martins and Pablo Saez
 * Date: 05.12.2023
 *
 * @brief:
 * Converters is a class containing type converters for Room database. It provides methods
 * to convert a Long timestamp to a Calendar instance and vice versa.
 */

package com.example.daa_lab04.room

import androidx.room.TypeConverter
import java.util.*

class Converters {
    @TypeConverter
    fun toCalendar(date: Long) =
        Calendar.getInstance().apply {
            time = Date(date)
        }

    @TypeConverter
    fun fromCalendar(date: Calendar) = date.time.time
}