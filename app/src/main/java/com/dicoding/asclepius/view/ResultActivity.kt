package com.dicoding.asclepius.view

import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.asclepius.R

class ResultActivity : AppCompatActivity() {
    private lateinit var resultsTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val resultImageView: ImageView = findViewById(R.id.result_image)
        resultsTextView = findViewById(R.id.result_text)
        val resultsText: String? = intent.getStringExtra("RESULTS")

        resultsTextView.text = resultsText ?: getString(R.string.no_results_found)
        val imageUriString = intent.getStringExtra("IMAGE_URI")

        imageUriString?.let { imageUri ->
            resultImageView.setImageURI(Uri.parse(imageUri))
        }
    }

}
