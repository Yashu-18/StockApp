package com.example.stock.data.remote

import com.example.stock.BuildConfig
import com.example.stock.data.models.Stocks
import com.example.stock.data.models.StockDescription
import com.example.stock.data.models.timeSeries.SeriesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AlphaVantageAPI {
    @GET("query")
    suspend fun getAllStocks(
        @Query("function") function: String = "TOP_GAINERS_LOSERS",
        @Query("apikey") apiKey: String = BuildConfig.STOCK_API_KEY
    ): Response<Stocks>

    @GET("query")
    suspend fun getDescriptionOfStockByTicker(
        @Query("function") function: String = "OVERVIEW",
        @Query("symbol") symbol: String = "IBM",
        @Query("apikey") apiKey: String = BuildConfig.STOCK_API_KEY
    ): Response<StockDescription>

    @GET("query")
    suspend fun getWeeklyTimeSeries(
        @Query("function") function: String = "TIME_SERIES_WEEKLY",
        @Query("symbol") symbol: String = "IBM",
        @Query("apikey") apiKey: String = BuildConfig.STOCK_API_KEY
    ): Response<SeriesResponse>

}