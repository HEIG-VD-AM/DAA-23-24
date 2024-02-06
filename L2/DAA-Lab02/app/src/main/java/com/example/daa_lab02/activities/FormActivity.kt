/**
 * Author: Martins Alexis, Saez Pablo
 * Description: This program represents the FormActivity for a simple Android application.
 * It allows the user to input their name and returns the result to the calling activity.
 */

package com.example.daa_lab02.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.daa_lab02.R

class FormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("ActivityLifeCycle: FormActivity", "onCreate")
        setContentView(R.layout.activity_main_form)

        // Initialize UI elements
        val validateUserName = findViewById<Button>(R.id.inputButton)
        val userNameInput = findViewById<EditText>(R.id.inputText)

        // Set the text in the EditText field from the intent
        userNameInput.setText(intent.getStringExtra(NAME))

        // Handle button click event
        validateUserName.setOnClickListener {
            // Get the field value from the EditText
            val fieldValue = userNameInput.text.toString()

            // Create an intent to send back the result
            val data = Intent()
            data.putExtra(NAME, fieldValue)

            // Set the result as OK and send the data back
            setResult(RESULT_OK, data)

            // Finish the activity
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("ActivityLifeCycle: FormActivity", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("ActivityLifeCycle: FormActivity", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("ActivityLifeCycle: FormActivity", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("ActivityLifeCycle: FormActivity", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("ActivityLifeCycle: FormActivity", "onDestroy")
    }

    companion object {
        const val NAME = "USER'S NAME"
    }
}
