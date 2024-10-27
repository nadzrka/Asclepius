@file:Suppress("unused", "RedundantSuppression")

package com.dicoding.asclepius.view.result

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.asclepius.R
import com.dicoding.asclepius.databinding.ActivityResultBinding
import com.dicoding.asclepius.data.local.entity.PredictionEntity
import com.dicoding.asclepius.view.ViewModelFactory
import com.dicoding.asclepius.view.article.ArticleActivity

class ResultActivity : AppCompatActivity() {
    private val resultViewModel by viewModels<ResultViewModel> {
        ViewModelFactory.Companion.getInstance(application)
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

        binding.articleButton.setOnClickListener {
            showArticle()
        }
    }

    private fun saveToDatabase(resultsText: String?, imageUriString: String?) {
        if (!resultsText.isNullOrEmpty() && !imageUriString.isNullOrEmpty()) {
            val predictionEntity = PredictionEntity(
                result = resultsText ,
                image = imageUriString
            )
            showToast("Prediction saved")
            resultViewModel.saveItem(predictionEntity)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun showArticle() {
        val intent = Intent(this, ArticleActivity::class.java)
        startActivity(intent)
    }
}
