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

        val resultsCategory: String? = intent.getStringExtra("RESULTS")
        val resultsScore: String? = intent.getStringExtra("SCORE")

        binding.categoryText.text = resultsCategory
        binding.scoreText.text = resultsScore

        val imageUriString = intent.getStringExtra("IMAGE_URI")
        imageUriString?.let { imageUri ->
            resultImageView.setImageURI(Uri.parse(imageUri))
        }

        binding.saveButton.setOnClickListener {
            saveToDatabase(resultsCategory, resultsScore, imageUriString)
        }

        binding.articleButton.setOnClickListener {
            showArticle()
        }
    }

    private fun saveToDatabase(resultsCategory: String?, resultScore: String?,imageUriString: String?) {
            val predictionEntity = PredictionEntity(
                category = resultsCategory ?: "",
                score = resultScore ?: "",
                image = imageUriString ?: ""
            )
            Toast.makeText(this, "Prediction saved", Toast.LENGTH_SHORT).show()
            resultViewModel.saveItem(predictionEntity)

    }

    fun showArticle() {
        val intent = Intent(this, ArticleActivity::class.java)
        startActivity(intent)
    }
}
