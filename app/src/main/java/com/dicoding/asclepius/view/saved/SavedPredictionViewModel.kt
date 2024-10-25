package com.dicoding.asclepius.view.saved

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.asclepius.data.local.entity.PredictionEntity
import com.dicoding.asclepius.view.Result
import kotlinx.coroutines.launch

class SavedPredictionViewModel(private val repository: PredictionRepository) : ViewModel() {

    fun getSavedPrediction(): LiveData<Result<List<PredictionEntity>>>{
        return repository.getPredictionItems()
    }

    suspend fun removeItem() = repository.removePrediction()
}