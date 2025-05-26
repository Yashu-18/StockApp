package com.example.stock.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.stock.data.local.StockDatabase.Companion.VERSION
import com.example.stock.data.models.StockDescription
import com.example.stock.data.models.TopStock
import com.example.stock.data.models.timeSeries.WeeklyData


@Database(entities = [TopStock::class, StockDescription::class, WeeklyData::class], version = VERSION)
abstract class StockDatabase : RoomDatabase() {

    abstract fun stocksDao(): StockDao
    abstract fun detailsDao(): DetailsDao

    companion object {

        const val VERSION = 1
        const val NAME = "stocks-db"

    }

}