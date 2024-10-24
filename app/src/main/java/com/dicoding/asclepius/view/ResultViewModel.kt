package com.dicoding.asclepius.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.asclepius.local.entity.PredictionEntity
import com.dicoding.asclepius.remote.PredictionRepository
import kotlinx.coroutines.launch

class ResultViewModel (private val repository: PredictionRepository) : ViewModel() {

    fun saveItem(item: PredictionEntity) {
        viewModelScope.launch {
            repository.setPrediction(item)
        }
    }
}