package com.dicoding.asclepius.view.saved

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import com.dicoding.asclepius.data.local.entity.PredictionEntity
import com.dicoding.asclepius.data.local.room.PredictionDao
import com.dicoding.asclepius.view.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PredictionRepository private constructor(
    private val predictionDao: PredictionDao
) {
    suspend fun setPrediction(prediction: PredictionEntity) {
        withContext(Dispatchers.IO) {
            predictionDao.insertItem(prediction)
        }
    }

    fun getPredictionItems(): LiveData<Result<List<PredictionEntity>>> {
        return predictionDao.getItem().asLiveData().map { items ->
            if (items.isNotEmpty()) Result.Success(items) else Result.Error("No saved items")
        }
    }


    suspend fun removePrediction(id: Int) {
        withContext(Dispatchers.IO) {
          predictionDao.deleteItem(id)
        }
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