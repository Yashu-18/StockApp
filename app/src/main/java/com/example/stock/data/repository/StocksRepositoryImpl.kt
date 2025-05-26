package com.example.stock.data.repository

import com.example.stock.presentation.util.Envelope
import com.example.stock.data.local.StockDatabase
import com.example.stock.data.models.ErrorModel
import com.example.stock.data.models.Stocks
import com.example.stock.data.models.StockDescription
import com.example.stock.data.models.TopStock
import com.example.stock.data.models.timeSeries.MetaData
import com.example.stock.data.models.timeSeries.SeriesResponse
import com.example.stock.data.models.timeSeries.WeeklyData
import com.example.stock.data.remote.AlphaVantageAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class StocksRepositoryImpl @Inject constructor(
    private val alphaVantageAPI: AlphaVantageAPI,
    private val stockDatabase: StockDatabase,
):StocksRepository {

    override fun getStocks(query: String): Flow<Envelope<Stocks>> = flow {
        emit(Envelope.loading())

        val cachedGainerStocks = fetchCachedStocks(query, type = 0)
        val cachedLoserStocks = fetchCachedStocks(query, type = 1)

        if (cachedGainerStocks.isNotEmpty() && isCacheValid(cachedGainerStocks[0].timestamp)) {
            emit(Envelope.success(Stocks(
                top_losers = cachedLoserStocks,
                top_gainers = cachedGainerStocks,
                metadata = "",
                last_updated = ""
            )))
            return@flow
        }

        val response = fetchStocksFromAPI()

        if (response.isSuccessful && response.body() != null) {
            saveStocksToDatabase(response.body()!!)
            emit(Envelope.success(response.body()!!.copy(
                top_losers = fetchCachedStocks(query, type = 1),
                top_gainers = fetchCachedStocks(query, type = 0)
            )))
        } else {
            emit(Envelope.Error(ErrorModel(message = response.message())))
        }
    }.catch { e ->
        emit(Envelope.Error(ErrorModel(message = e.message)))
        e.printStackTrace()
    }


    override fun getStockDescriptionByTicker(ticker: String): Flow<Envelope<StockDescription>> = flow {
        emit(Envelope.loading())

        val cachedDescription = fetchCachedDescription(ticker)

        if (cachedDescription != null && isCacheValid(cachedDescription.timestamp)) {
            emit(Envelope.success(cachedDescription))
            return@flow
        }

        val response = fetchStockDescriptionFromAPI(ticker)

        if (response.isSuccessful && response.body() != null) {
            saveStockDescriptionToDatabase(response.body()!!, ticker)
            emit(Envelope.success(response.body()!!))
        } else {
            emit(Envelope.Error(ErrorModel(message = response.message())))
        }
    }.catch { e ->
        emit(Envelope.Error(ErrorModel(message = e.message)))
        e.printStackTrace()
    }


    override fun getWeeklyTimeSeries(ticker: String): Flow<Envelope<SeriesResponse>> = flow {
        emit(Envelope.loading())

        val cachedTimeSeries = fetchCachedWeeklyTimeSeries(ticker)

        if (cachedTimeSeries != null && cachedTimeSeries.isNotEmpty()) {
            val isCacheValid = isCacheValid(cachedTimeSeries[0].timestamp)
            if (isCacheValid) {
                emit(Envelope.success(cachedTimeSeries.toSeriesResponse()))
                return@flow
            }
        }

        val response = fetchWeeklyTimeSeriesFromAPI(ticker)

        if (response.isSuccessful) {
            response.body()?.let { body ->
                saveWeeklyTimeSeriesToDatabase(body, ticker)
                emit(Envelope.success(body))
            } ?: emit(Envelope.Error(ErrorModel(message = "Empty response body")))
        } else {
            emit(Envelope.Error(ErrorModel(message = response.message())))
        }
    }.catch { e ->
        emit(Envelope.Error(ErrorModel(message = e.message ?: "Unknown error occurred")))
        e.printStackTrace()
    }

    private suspend fun fetchCachedStocks(query: String, type: Int): List<TopStock> {
        return withContext(Dispatchers.IO) {
            stockDatabase.stocksDao().searchCompanyListing(query = query, type = type)
        }
    }

    private suspend fun fetchStocksFromAPI(): Response<Stocks> {
        return withContext(Dispatchers.IO) {
            alphaVantageAPI.getAllStocks()
        }
    }

    private suspend fun saveStocksToDatabase(stocks: Stocks) {
        withContext(Dispatchers.IO) {
            stockDatabase.stocksDao().deleteAll()
            val currentTime = System.currentTimeMillis()
            stocks.top_gainers.forEach {
                stockDatabase.stocksDao().insertStocks(it.copy(type = 0, timestamp = currentTime))
            }
            stocks.top_losers.forEach {
                stockDatabase.stocksDao().insertStocks(it.copy(type = 1, timestamp = currentTime))
            }
        }
    }

    private suspend fun fetchCachedDescription(ticker: String): StockDescription? {
        return withContext(Dispatchers.IO) {
            stockDatabase.detailsDao().getDetails(ticker).firstOrNull()
        }
    }

    private suspend fun fetchStockDescriptionFromAPI(ticker: String): Response<StockDescription> {
        return withContext(Dispatchers.IO) {
            alphaVantageAPI.getDescriptionOfStockByTicker(symbol = ticker)
        }
    }

    private suspend fun saveStockDescriptionToDatabase(description: StockDescription, ticker: String) {
        withContext(Dispatchers.IO) {
            stockDatabase.detailsDao().apply {
                deleteTickerDetail(ticker)
                insertDetails(description.copy(timestamp = System.currentTimeMillis()))
            }
        }
    }

    private suspend fun fetchCachedWeeklyTimeSeries(ticker: String): List<WeeklyData>? {
        return withContext(Dispatchers.IO) {
            stockDatabase.detailsDao().getWeeklyData(ticker)
        }
    }

    private suspend fun fetchWeeklyTimeSeriesFromAPI(ticker: String): Response<SeriesResponse> {
        return withContext(Dispatchers.IO) {
            alphaVantageAPI.getWeeklyTimeSeries(symbol = ticker)
        }
    }

    private suspend fun saveWeeklyTimeSeriesToDatabase(body: SeriesResponse, ticker: String) {
        withContext(Dispatchers.IO) {
            stockDatabase.detailsDao().deleteTimeSeries(ticker)
            val currentTime = System.currentTimeMillis()
            body.weeklyTimeSeries?.forEach { (key, value) ->
                stockDatabase.detailsDao().insertWeeklyData(
                    value.copy(timestamp = currentTime, key = key, ticker = ticker)
                )
            }
        }
    }

    private fun List<WeeklyData>.toSeriesResponse(): SeriesResponse {
        val data = this.associateBy { it.key }
        return SeriesResponse(weeklyTimeSeries = data, metaData = MetaData("", "", "", ""))
    }

    private fun isCacheValid(timestamp: Long): Boolean {
        val currentTime = System.currentTimeMillis()
        val expirationTime = 3600 * 1000

        return currentTime - timestamp <= expirationTime
    }

}