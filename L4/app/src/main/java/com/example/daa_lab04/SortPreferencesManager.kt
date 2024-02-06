/**
 * Authors: Alexis Martins and Pablo Saez
 * Date: 05.12.2023
 *
 * @brief:
 * SortPreferencesManager is a utility class for managing the sorting preferences of the application.
 * It uses SharedPreferences to store and retrieve the selected sorting type. The class provides
 * methods to save and retrieve the sorting type, and it includes constants for the preference name
 * and key. The default sorting type is set to SortType.ETA if no value is found.
 */

package com.example.daa_lab04

import android.content.Context
import android.content.SharedPreferences
import com.example.daa_lab04.viewModels.SortType

class SortPreferencesManager(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveSortType(sortType: SortType) {
        sharedPreferences.edit().putString(KEY_SORT_TYPE, sortType.name).apply()
    }

    fun getSortType(): SortType {
        val savedSortType = sharedPreferences.getString(KEY_SORT_TYPE, SortType.ETA.name)
        return SortType.valueOf(savedSortType ?: SortType.ETA.name)
    }

    companion object {
        private const val PREFS_NAME = "SortPreferences"
        private const val KEY_SORT_TYPE = "SortType"
    }
}