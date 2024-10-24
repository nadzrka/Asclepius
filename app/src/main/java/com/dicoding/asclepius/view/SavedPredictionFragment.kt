package com.dicoding.asclepius.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.databinding.FragmentSavedPredictionBinding

class SavedPredictionFragment : Fragment() {
    private var _binding: FragmentSavedPredictionBinding? = null
    private val binding get() = _binding!!
    private val savedPredictionViewModel by viewModels<SavedPredictionViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }
    private lateinit var predictionAdapter: PredictionAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSavedPredictionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        savedPredictionViewModel.getSavedPrediction().observe(viewLifecycleOwner) { result ->
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
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
