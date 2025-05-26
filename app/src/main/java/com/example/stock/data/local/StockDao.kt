package com.example.stock.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.stock.data.models.Stocks
import com.example.stock.data.models.TopStock
import kotlinx.coroutines.flow.Flow

@Dao
interface StockDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStocks(topStock: TopStock)

    @Query("SELECT * FROM stock where type = :type")
    fun getAllStocks(type: Int): Flow<List<TopStock>>

    @Query("Delete from stock")
    suspend fun deleteAll()

    @Query("select * from stock where (type =:type) and lower(ticker) like '%' || lower(:query) || '%' or upper(:query) == ticker")
    suspend fun searchCompanyListing(query: String, type: Int): List<TopStock>
}