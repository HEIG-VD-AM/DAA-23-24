/**
 * Author: Martins Alexis, Saez Pablo
 * Description: This program defines the FormContract class, which is an Activity Result Contract
 * used to start the FormActivity and retrieve a result (a user's name) from it.
 */

package com.example.daa_lab02.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

class FormContract : ActivityResultContract<String, String>() {
    /**
     * Create an intent for starting the FormActivity with the provided input (a user's name).
     */
    override fun createIntent(context: Context, input: String): Intent =
        Intent(context, FormActivity::class.java).apply {
            putExtra(FormActivity.NAME, input)
        }

    /**
     * Parse the result from the FormActivity and return the user's name.
     */
    override fun parseResult(resultCode: Int, result: Intent?): String {
        return if (resultCode == Activity.RESULT_OK) {
            result?.getStringExtra(FormActivity.NAME) ?: ""
        } else {
            ""
        }
    }
}
