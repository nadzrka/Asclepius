package com.dicoding.asclepius.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.databinding.ActivitySavedPredictionBinding

class SavedPredictionActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySavedPredictionBinding
    private val savedPredictionViewModel by viewModels<SavedPredictionViewModel> {
        ViewModelFactory.getInstance(application)
    }
    private lateinit var predictionAdapter: PredictionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySavedPredictionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        savedPredictionViewModel.getSavedPrediction().observe(this) { result ->
            when (result) {
                is Result.Success -> {
                    predictionAdapter.submitList(result.data)
                }
                is Result.Error -> {
                    showToast("Error loading predictions: ${result.error}")
                }
                is Result.Loading -> {
                    showToast("Loading predictions...")
                }
            }
        }
    }

    private fun setupRecyclerView() {
        predictionAdapter = PredictionAdapter()
        binding.rvItems.apply {
            adapter = predictionAdapter
            layoutManager = LinearLayoutManager(this@SavedPredictionActivity)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
