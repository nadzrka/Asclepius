package com.dicoding.asclepius.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import com.dicoding.asclepius.R
import com.dicoding.asclepius.databinding.ActivityMainBinding
import com.dicoding.asclepius.helper.ImageClassifierHelper
import com.dicoding.asclepius.view.article.ArticleActivity
import com.dicoding.asclepius.view.result.ResultActivity
import com.dicoding.asclepius.view.saved.PredictionActivity
import com.yalantis.ucrop.UCrop
import java.io.File

class MainActivity : AppCompatActivity(), ImageClassifierHelper.ClassifierListener {
    private lateinit var binding: ActivityMainBinding
    private var currentImageUri: Uri? = null
    private lateinit var imageClassifierHelper: ImageClassifierHelper

    private val cropImageLauncher = registerForActivityResult(StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            result.data?.let { intent ->
                val croppedImageUri = UCrop.getOutput(intent)
                if (croppedImageUri != null) {
                    currentImageUri = croppedImageUri
                    showImage()
                } else {
                    showToast("Cropping failed.")
                }
            }
        } else {
            showToast("Cropping was cancelled.")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.galleryButton.setOnClickListener { startGallery() }
        binding.analyzeButton.setOnClickListener { analyzeImage() }
        binding.savedPredictionButton.setOnClickListener { showSavedPrediction() }

        binding.articleButton.setOnClickListener {
            showArticle()
        }

        imageClassifierHelper = ImageClassifierHelper(context = this, classifierListener = this)
    }

    private fun startCrop(uri: Uri) {
        val destinationUri = Uri.fromFile(File(this.cacheDir,  "cropped_image_${System.currentTimeMillis()}.jpg"))

        val uCropIntent = UCrop.of(uri, destinationUri)
            .withAspectRatio(1f, 1f)
            .withMaxResultSize(224, 224)
            .getIntent(this)

        cropImageLauncher.launch(uCropIntent)
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
            startCrop(uri)
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            binding.previewImageView.setImageURI(it)
        }
    }

    private fun analyzeImage() {
        currentImageUri?.let {
            imageClassifierHelper.classifyStaticImage(it)
        } ?: showToast(getString(R.string.no_image_selected))
    }

    override fun onError(error: String) {
        showToast(error)
    }

    override fun onResults(resultsCategory: String, resultsScore: String) {
        moveToResult(resultsCategory, resultsScore)
    }

    private fun moveToResult(resultsCategory: String, resultsScore: String) {
        currentImageUri?.let { imageUri ->
            val intent = Intent(this, ResultActivity::class.java).apply {
                putExtra("RESULTS", resultsCategory)
                putExtra("SCORE", resultsScore)
                putExtra("IMAGE_URI", imageUri.toString())
            }
            startActivity(intent)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun showSavedPrediction() {
        val intent = Intent(this, PredictionActivity::class.java)
        startActivity(intent)
    }


    fun showArticle() {
        val intent = Intent(this, ArticleActivity::class.java)
        startActivity(intent)
    }

}
