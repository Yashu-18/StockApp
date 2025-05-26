package com.example.stock.data.models.timeSeries

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity("weekly_data")
data class WeeklyData(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo("ticker")
    val ticker: String,
    @SerializedName("1. open")
    val open: String,
    @SerializedName("2. high")
    val high: String,
    @SerializedName("3. low")
    val low: String,
    @SerializedName("4. close")
    val close: String,
    @SerializedName("5. volume")
    val volume: String,
    @ColumnInfo("timestamp")
    val timestamp: Long = System.currentTimeMillis(),
    @ColumnInfo("key")
    val key: String = "",


)