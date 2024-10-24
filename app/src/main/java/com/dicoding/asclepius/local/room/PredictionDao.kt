@file:Suppress("unused", "RedundantSuppression")

package com.dicoding.asclepius.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dicoding.asclepius.local.entity.PredictionEntity

@Dao
interface PredictionDao {

    @Query("SELECT * FROM prediction")
    fun getItem(): LiveData<List<PredictionEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertItem(prediction: PredictionEntity)

    @Update
    suspend fun updateItem(item: PredictionEntity)

    @Query("DELETE FROM prediction")
    suspend fun deleteItem()

    @Query("SELECT * FROM prediction WHERE id = :itemId")
    fun getItemById(itemId: Int): LiveData<PredictionEntity>
}