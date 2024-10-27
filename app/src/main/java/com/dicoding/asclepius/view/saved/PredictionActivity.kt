package com.dicoding.asclepius.view.saved

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.dicoding.asclepius.databinding.ActivityPredictionBinding
import com.dicoding.asclepius.view.Result
import com.dicoding.asclepius.view.ViewModelFactory

class PredictionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPredictionBinding
    private val predictionViewModel by viewModels<PredictionViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var predictionAdapter: PredictionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPredictionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        predictionViewModel.getSavedPrediction().observe(this) { result ->
            when (result) {
                is Result.Success -> {
                    binding.rvItems.visibility = View.VISIBLE
                    binding.tvNoEvent.visibility = View.GONE
                    predictionAdapter.submitList(result.data)
                }
                is Result.Error -> {
                    binding.rvItems.visibility = View.GONE
                    binding.tvNoEvent.visibility = View.VISIBLE
                    showToast(result.error)
                }
                is Result.Loading -> {
                    showToast("Loading predictions...")
                }
            }
        }
    }

    private fun setupRecyclerView() {
        predictionAdapter = PredictionAdapter(predictionViewModel)
        binding.rvItems.apply {
            adapter = predictionAdapter
            layoutManager = GridLayoutManager(this@PredictionActivity, 2)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
