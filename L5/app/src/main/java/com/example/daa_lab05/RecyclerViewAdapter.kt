/*
 * RecyclerViewAdapter.kt
 * Authors: Alexis Martins, Pablo Saez
 * Description: This file defines the RecyclerViewAdapter used to display images
 *              in the RecyclerView. It includes coroutine-based image loading,
 *              caching, and click handling.
 */

package com.example.daa_lab05

import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.URL

interface OnItemClickListener {
    fun onItemClick(position: Int)
}

class RecyclerViewAdapter(
    private val coroutineScope: CoroutineScope,
    private val cacheDir: File,
    private val listener: OnItemClickListener) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val image: ImageView = itemView.findViewById(R.id.displayedImage)
        private val progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)
        private var onGoingJob: Job? = null

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }

        fun bind(position: Int) {
            // Cancel the ongoing job if there is any
            onGoingJob?.cancel()

            // Show progress bar while loading
            progressBar.visibility = View.VISIBLE

            // Launch a coroutine for each item
            onGoingJob = coroutineScope.launch {
                // Try to read from cache
                val bmp = readCache("https://daa.iict.ch/images/$position.jpg")

                // If not in cache, download and write to cache
                if (bmp == null) {
                    val downloadedImage = downloadImage(position)
                    writeCache("https://daa.iict.ch/images/$position.jpg", downloadedImage)
                    displayImage(image, downloadedImage)
                } else {
                    // If in cache, directly display
                    displayImage(image, bmp)
                }

                // Hide progress bar after loading
                progressBar.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 10000
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    private fun readCache(url: String): Bitmap? {
        val file = File(cacheDir, url.hashCode().toString())
        return if (file.exists()) BitmapFactory.decodeFile(file.absolutePath) else null
    }

    private fun writeCache(url: String, bitmap: Bitmap) {
        val file = File(cacheDir, url.hashCode().toString())
        FileOutputStream(file).use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
        }
    }

    private suspend fun downloadImage(nbImage: Int): Bitmap = withContext(Dispatchers.IO) {
        try {
            val input: InputStream = URL("https://daa.iict.ch/images/$nbImage.jpg").openStream()
            BitmapFactory.decodeStream(input)
        } catch (e: IOException) {
            Log.e(TAG, "Error while downloading image", e)
            throw e
        }
    }

    private suspend fun displayImage(myImage: ImageView, bmp: Bitmap?) = withContext(Dispatchers.Main) {
        myImage.setImageBitmap(bmp)
    }
}
