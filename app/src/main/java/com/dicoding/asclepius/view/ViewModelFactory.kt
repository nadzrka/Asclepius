package com.dicoding.asclepius.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.asclepius.view.saved.PredictionRepository
import com.dicoding.asclepius.view.article.ArticleViewModel
import com.dicoding.asclepius.view.result.ResultViewModel
import com.dicoding.asclepius.view.saved.PredictionViewModel

class ViewModelFactory private constructor(
    private val predictionRepository: PredictionRepository
    ) : ViewModelProvider.NewInstanceFactory() {

        @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ResultViewModel::class.java) -> {
                ResultViewModel(predictionRepository) as T
            }

            modelClass.isAssignableFrom(PredictionViewModel::class.java) -> {
                PredictionViewModel(predictionRepository) as T
            }

            modelClass.isAssignableFrom(ArticleViewModel::class.java) -> {
                ArticleViewModel() as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    Injection.providePredictionRepository(context)
                ).also { instance = it }
            }
    }
}
