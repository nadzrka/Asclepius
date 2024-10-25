package com.dicoding.asclepius.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.asclepius.data.local.entity.PredictionEntity
import com.dicoding.asclepius.PredictionRepository

class SavedPredictionViewModel(private val repository: PredictionRepository) : ViewModel() {

    fun getSavedPrediction(): LiveData<Result<List<PredictionEntity>>>{
        return repository.getPredictionItems()
    }
}