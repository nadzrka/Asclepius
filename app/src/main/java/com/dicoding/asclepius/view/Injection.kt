package com.dicoding.asclepius.view

import android.content.Context
import com.dicoding.asclepius.data.local.room.PredictionDatabase
import com.dicoding.asclepius.view.saved.PredictionRepository

object Injection {
    fun providePredictionRepository(context: Context): PredictionRepository {
        val database = PredictionDatabase.getInstance(context)
        val dao = database.eventDao()
        return PredictionRepository.getInstance(dao)
    }
}