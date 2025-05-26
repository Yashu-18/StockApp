package com.example.stock.data.repository

import com.example.stock.presentation.util.Envelope
import com.example.stock.data.models.StockDescription
import com.example.stock.data.models.Stocks
import com.example.stock.data.models.timeSeries.SeriesResponse
import kotlinx.coroutines.flow.Flow

interface StocksRepository {
    fun getStocks(query: String): Flow<Envelope<Stocks>>
    fun getStockDescriptionByTicker(ticker: String): Flow<Envelope<StockDescription>>
    fun getWeeklyTimeSeries(ticker: String): Flow<Envelope<SeriesResponse>>
}