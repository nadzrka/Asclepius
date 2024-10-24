@file:Suppress("unused", "RedundantSuppression")

package com.dicoding.asclepius.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.asclepius.local.entity.PredictionEntity
import com.dicoding.asclepius.local.room.PredictionDao
import com.dicoding.asclepius.view.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PredictionRepository private constructor(
    private val predictionDao: PredictionDao
) {
    private val _showToastMessage = MutableLiveData<String>()
    val showToastMessage: LiveData<String> get() = _showToastMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    suspend fun setPrediction(prediction: PredictionEntity) {
        withContext(Dispatchers.IO) {
            predictionDao.insertItem(prediction)
        }
    }

    fun getPredictionItems(): LiveData<Result<List<PredictionEntity>>> {
        val result = MediatorLiveData<Result<List<PredictionEntity>>>()
        result.value = Result.Loading
        val localData = predictionDao.getItem()
        result.addSource(localData) { items ->
            if (items.isNotEmpty()) {
                result.value = Result.Success(items)
            } else {
                result.value = Result.Error("No favorite events found")
            }
        }

        return result
    }


    fun getPredictionById(id: Int): LiveData<PredictionEntity> {
        return predictionDao.getItemById(id)
    }

    companion object {
        @Volatile
        private var instance: PredictionRepository? = null
        fun getInstance(
            predictionDao: PredictionDao
        ): PredictionRepository =
            instance ?: synchronized(this) {
                instance ?: PredictionRepository(predictionDao)
            }.also { instance = it }
    }
}