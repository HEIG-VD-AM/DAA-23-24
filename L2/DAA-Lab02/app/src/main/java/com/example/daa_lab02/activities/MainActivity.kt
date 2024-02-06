/**
 * Author: Martins Alexis, Saez Pablo
 * Description: This program represents the MainActivity for a simple Android application.
 * It allows the user to input their name and displays a welcome message using a result contract.
 */

package com.example.daa_lab02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.example.daa_lab02.activities.FormActivity
import com.example.daa_lab02.activities.FormContract

class MainActivity : AppCompatActivity() {
    private var name = ""

    /**
     * Result contract for getting the user's name.
     * When the result is received, it updates the 'name' property and calls 'writeUserName()'.
     */
    private val getName = registerForActivityResult(FormContract()) { result ->
        result?.let {
            name = it
        }
        writeUserName()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("ActivityLifeCycle: MainActivity", "onCreate")
        setContentView(R.layout.activity_main)

        // Find the 'mainButton' view by its ID and set a click listener
        val mainButton = findViewById<Button>(R.id.mainButton)
        mainButton.setOnClickListener {
            // Launch the 'getName' activity to get the user's name
            getName.launch(name)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Save the 'name' property to the savedInstanceState bundle
        outState.putString(FormActivity.NAME, name)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // Restore the 'name' property from the savedInstanceState bundle
        name = savedInstanceState.getString(FormActivity.NAME, "")
        writeUserName()
    }

    /**
     * Updates the 'mainText' view based on the 'name' property.
     * If 'name' is empty, a default welcome question is displayed. Otherwise, a welcome message
     * with the user's name is displayed.
     */
    fun writeUserName() {
        val mainText = findViewById<TextView>(R.id.mainText)
        if (name.isEmpty()) {
            mainText.text = getString(R.string.welcomeQuestion)
        } else {
            mainText.text = getString(R.string.welcomeWithName, name)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("ActivityLifeCycle: MainActivity", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("ActivityLifeCycle: MainActivity", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("ActivityLifeCycle: MainActivity", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("ActivityLifeCycle: MainActivity", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("ActivityLifeCycle: MainActivity", "onDestroy")
    }
}
