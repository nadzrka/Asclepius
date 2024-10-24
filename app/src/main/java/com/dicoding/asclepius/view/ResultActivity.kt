package com.dicoding.asclepius.view

import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.asclepius.R
import com.dicoding.asclepius.databinding.ActivityResultBinding
import com.dicoding.asclepius.local.entity.PredictionEntity

class ResultActivity : AppCompatActivity() {
    private val resultViewModel by viewModels<ResultViewModel> {
        ViewModelFactory.getInstance(application)
    }
    private lateinit var binding: ActivityResultBinding
    private lateinit var resultsTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val resultImageView: ImageView = findViewById(R.id.result_image)
        resultsTextView = findViewById(R.id.result_text)

        val resultsText: String? = intent.getStringExtra("RESULTS")
        resultsTextView.text = resultsText ?: getString(R.string.no_results_found)

        val imageUriString = intent.getStringExtra("IMAGE_URI")
        imageUriString?.let { imageUri ->
            resultImageView.setImageURI(Uri.parse(imageUri))
        }

        binding.saveButton.setOnClickListener {
            saveToDatabase(resultsText, imageUriString)
        }
    }

    private fun saveToDatabase(resultsText: String?, imageUriString: String?) {
        if (!resultsText.isNullOrEmpty() && !imageUriString.isNullOrEmpty()) {
            val predictionEntity = PredictionEntity(
                result = resultsText ,
                image = imageUriString
            )
            resultViewModel.saveItem(predictionEntity)
        }
    }
}
