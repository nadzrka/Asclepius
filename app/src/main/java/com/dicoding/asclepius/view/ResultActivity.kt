package com.dicoding.asclepius.view

import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.asclepius.R
import java.util.Locale

class ResultActivity : AppCompatActivity() {
    private lateinit var resultsTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val resultImageView: ImageView = findViewById(R.id.result_image)
        resultsTextView = findViewById(R.id.result_text)

        val imageUriString = intent.getStringExtra("IMAGE_URI")
        val results: ArrayList<Float>? = intent.getSerializableExtra("RESULTS") as? ArrayList<Float>

        imageUriString?.let { imageUri ->
            resultImageView.setImageURI(Uri.parse(imageUri))
        }

        displayResults(results)
    }

    private fun displayResults(results: ArrayList<Float>?) {
        if (!results.isNullOrEmpty()) {
            val maxResult = results.maxOrNull() ?: 0f
            resultsTextView.text =  String.format(Locale.US, "%.2f%%", maxResult * 100)
        } else {
            resultsTextView.text = getString(R.string.no_results_found)
        }
    }
}
