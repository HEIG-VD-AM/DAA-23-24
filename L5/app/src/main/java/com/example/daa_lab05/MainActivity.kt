/*
 * MainActivity.kt
 * Authors: Alexis Martins, Pablo Saez
 * Description: This file defines the main activity of the Android application.
 *              It sets up the RecyclerView for displaying images and configures WorkManager
 *              for background tasks. It includes functions for initializing the RecyclerView,
 *              setting up WorkManager, and handling menu options.
 */

package com.example.daa_lab05

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.daa_lab05.databinding.MainActivityBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize RecyclerView
        setupRecyclerView()

        // Initialize WorkManager for cache clearing
        setupWorkManager()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            // Set up the RecyclerView adapter and click listener
            adapter = RecyclerViewAdapter(lifecycleScope, cacheDir, object : OnItemClickListener {
                override fun onItemClick(position: Int) {
                    val intent = Intent(this@MainActivity, FullScreenImageActivity::class.java)
                    intent.putExtra("imageUrl", "https://daa.iict.ch/images/$position.jpg")
                    startActivity(intent)
                }
            })
            layoutManager = GridLayoutManager(this@MainActivity, 3)
        }
    }

    private fun setupWorkManager() {
        val workManager = WorkManager.getInstance(applicationContext)

        // Configure periodic cache clearing task
        val cacheClearer = PeriodicWorkRequestBuilder<ClearCacheWorker>(15, TimeUnit.MINUTES).build()
        workManager.enqueue(cacheClearer)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.main_menu -> {
            // Trigger cache clearing task and refresh the RecyclerView
            WorkManager.getInstance(applicationContext)
                .enqueue(OneTimeWorkRequestBuilder<ClearCacheWorker>().build())
            binding.recyclerView.adapter?.notifyDataSetChanged()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }
}
