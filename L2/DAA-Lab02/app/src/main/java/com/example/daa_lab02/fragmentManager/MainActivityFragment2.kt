/**
 * Authors: Martins Alexis, Pablo Saez
 * Description: This program represents MainActivityFragment2, an AppCompatActivity that displays
 * the layout defined in R.layout.activity_main_fragment2. It allows navigation between steps using
 * a fragment manager.
 */
package com.example.daa_lab02.fragmentManager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.daa_lab02.R

class MainActivityFragment2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_fragment2)

        // Initialize UI elements
        val button_back = findViewById<Button>(R.id.button_back)
        val button_close = findViewById<Button>(R.id.button_close)
        val button_next = findViewById<Button>(R.id.button_next)

        if (supportFragmentManager.backStackEntryCount == 0) {
            newStep()
        }

        button_back.setOnClickListener {
            if (supportFragmentManager.backStackEntryCount == 1) {
                finish()
            }
            supportFragmentManager.popBackStack()
        }

        button_close.setOnClickListener {
            finish()
        }

        button_next.setOnClickListener {
            newStep()
        }
    }

    fun newStep() {
        // Create a new fragment representing a step
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.fragment_manager,
                Step.newInstance(supportFragmentManager.backStackEntryCount + 1)
            )
            .addToBackStack(null)
            .commit()
    }
}
