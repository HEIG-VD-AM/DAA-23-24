/**
 * Authors: Martins Alexis, Pablo Saez
 * Description: This program defines the Step class, which is a fragment used for displaying a step
 * in a multi-step process. It takes a step counter and displays it in the layout.
 */
package com.example.daa_lab02.fragmentManager

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.daa_lab02.R

private const val STEP_COUNTER = "stepX"

class Step : Fragment() {
    private var currentStep: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            currentStep = it.getInt(STEP_COUNTER)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_unit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Find and update the text view with the current step
        val text = view.findViewById<TextView>(R.id.step)
        text.text = String.format(getString(R.string.step), currentStep)
    }

    companion object {
        @JvmStatic
        fun newInstance(cnt: Int) =
            Step().apply {
                arguments = Bundle().apply {
                    putInt(STEP_COUNTER, cnt)
                }
            }
    }
}
