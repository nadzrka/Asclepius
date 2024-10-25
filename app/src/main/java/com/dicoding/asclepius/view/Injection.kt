@file:Suppress("unused", "RedundantSuppression")

package com.dicoding.asclepius.view

import android.content.Context
import com.dicoding.asclepius.data.local.room.PredictionDatabase
import com.dicoding.asclepius.PredictionRepository
import com.dicoding.asclepius.data.remote.retrofit.ApiConfig

object Injection {
    fun providePredictionRepository(context: Context): PredictionRepository {
        val database = PredictionDatabase.getInstance(context)
        val dao = database.eventDao()
        return PredictionRepository.getInstance(dao)
    }
}