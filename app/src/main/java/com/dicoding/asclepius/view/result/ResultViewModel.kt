package com.dicoding.asclepius.view.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.asclepius.data.local.entity.PredictionEntity
import com.dicoding.asclepius.view.saved.PredictionRepository
import kotlinx.coroutines.launch

class ResultViewModel (private val repository: PredictionRepository) : ViewModel() {

    fun saveItem(item: PredictionEntity) {
        viewModelScope.launch {
            repository.setPrediction(item)
        }
    }
}