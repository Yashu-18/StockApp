package com.example.stock.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("stock")
data class TopStock(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo("type")
    val type: Int = 0,
    @ColumnInfo("timestamp")
    val timestamp: Long = System.currentTimeMillis(),
    val change_amount: String,
    val change_percentage: String,
    val price: String,
    val ticker: String,
    val volume: String
)