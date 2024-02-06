/**
 * Author: Martins Alexis, Pablo Saez
 * Description: This program represents MainActivityFragment1, an AppCompatActivity that displays
 * the layout defined in R.layout.activity_main_fragment.
 */
package com.example.daa_lab02.fragments

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.daa_lab02.R

class MainActivityFragment1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set the content view to the layout defined in R.layout.activity_main_fragment
        setContentView(R.layout.activity_main_fragment)
    }
}
