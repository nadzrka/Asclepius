package com.dicoding.asclepius.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.asclepius.local.entity.PredictionEntity
import com.dicoding.asclepius.remote.PredictionRepository

class SavedPredictionViewModel(private val repository: PredictionRepository) : ViewModel() {

    fun getSavedPrediction(): LiveData<Result<List<PredictionEntity>>>{
        return repository.getPredictionItems()
    }
}