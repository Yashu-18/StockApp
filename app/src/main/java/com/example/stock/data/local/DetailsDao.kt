package com.example.stock.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.stock.data.models.StockDescription
import com.example.stock.data.models.timeSeries.WeeklyData

@Dao
interface DetailsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetails(stockDescription: StockDescription)

    @Query("SELECT * FROM details where Symbol = :ticker")
    fun getDetails(ticker: String): List<StockDescription>

    @Query("Delete from details where Symbol = :ticker")
    suspend fun deleteTickerDetail(ticker: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeeklyData(weeklyData: WeeklyData)

    @Query("SELECT * FROM weekly_data where ticker = :ticker")
    fun getWeeklyData(ticker: String): List<WeeklyData>?

    @Query("Delete from weekly_data where ticker = :ticker")
    suspend fun deleteTimeSeries(ticker: String)

}