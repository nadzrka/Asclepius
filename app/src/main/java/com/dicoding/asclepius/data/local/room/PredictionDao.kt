@file:Suppress("unused", "RedundantSuppression")

package com.dicoding.asclepius.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dicoding.asclepius.data.local.entity.PredictionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PredictionDao {

    @Query("SELECT * FROM prediction")
    fun getItem(): Flow<List<PredictionEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertItem(prediction: PredictionEntity)

    @Query("SELECT * FROM prediction WHERE id = :id")
    fun getPredictionById(id: Int): PredictionEntity

    @Update
    suspend fun updateItem(item: PredictionEntity)

    @Query("DELETE FROM prediction WHERE id = :itemId")
    suspend fun deleteItem(itemId: Int): Int

    @Query("SELECT * FROM prediction WHERE id = :itemId")
    fun getItemById(itemId: Int): LiveData<PredictionEntity>
}