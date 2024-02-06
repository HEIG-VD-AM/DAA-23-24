/*
 * FullScreenImageActivity.kt
 * Authors: Alexis Martins, Pablo Saez
 * Description: This file defines the activity for displaying a full-screen image.
 *              It loads and displays the image from the provided URL.
 */

package com.example.daa_lab05

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.daa_lab05.databinding.ActivityFullScreenImageBinding
import java.net.URL

class FullScreenImageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFullScreenImageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFullScreenImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get the image URL passed from the previous activity
        val imageUrl = intent.getStringExtra("imageUrl")
        imageUrl?.let { displayImageFromUrl(it) }
    }

    private fun displayImageFromUrl(url: String) {
        Thread {
            // Download and decode the image from the URL
            val bmp = BitmapFactory.decodeStream(URL(url).openStream())

            // Update the UI on the main thread to display the image
            runOnUiThread {
                binding.imageViewFullScreen.setImageBitmap(bmp)
            }
        }.start()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.fullscreen_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_return -> {
                // Finish the activity and return to the previous screen
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
