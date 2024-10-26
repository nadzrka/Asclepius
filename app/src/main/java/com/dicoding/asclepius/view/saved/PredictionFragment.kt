@file:Suppress("unused", "RedundantSuppression")

package com.dicoding.asclepius.view.saved

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.dicoding.asclepius.databinding.FragmentSavedPredictionBinding
import com.dicoding.asclepius.view.Result
import com.dicoding.asclepius.view.ViewModelFactory

class PredictionFragment : Fragment() {
    private var _binding: FragmentSavedPredictionBinding? = null
    private val binding get() = _binding!!
    private val predictionViewModel by viewModels<PredictionViewModel> {
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
        predictionViewModel.getSavedPrediction().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Success -> {
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
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
