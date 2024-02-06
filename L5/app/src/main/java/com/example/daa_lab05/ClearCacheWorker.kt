package com.example.daa_lab05

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.io.File

class ClearCacheWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        return if (clearCacheDirectory(applicationContext.cacheDir)) {
            Result.success()
        } else {
            Result.failure()
        }
    }

    private fun clearCacheDirectory(directory: File): Boolean {
        val files = directory.listFiles()

        if (files == null || files.isEmpty()) {
            return true // If the directory is empty or not a directory, we consider it as 'cleared'
        }

        return files.all { it.delete() } // Return true if all files are successfully deleted, otherwise false
    }
}
