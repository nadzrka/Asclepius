@file:Suppress("unused", "RedundantSuppression")

package com.dicoding.asclepius.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "prediction")
data class PredictionEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "image")
    val image: String,

    @ColumnInfo(name = "result")
    val result: String
)
