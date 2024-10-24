@file:Suppress("unused", "RedundantSuppression")

package com.dicoding.asclepius.view

import android.content.Context
import com.dicoding.asclepius.local.room.PredictionDatabase
import com.dicoding.asclepius.remote.PredictionRepository

object Injection {
    fun providePredictionRepository(context: Context): PredictionRepository {
        val database = PredictionDatabase.getInstance(context)
        val dao = database.eventDao()
        return PredictionRepository.getInstance(dao)
    }
}