package com.example.stock.presentation.screens.home

import androidx.lifecycle.viewModelScope
import com.example.stock.presentation.util.Envelope
import com.example.stock.data.models.ErrorModel
import com.example.stock.data.models.StockDescription
import com.example.stock.data.models.TopStock
import com.example.stock.data.repository.StocksRepositoryImpl
import com.example.stock.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val repository: StocksRepositoryImpl
) : BaseViewModel<HomeContract.Event, HomeContract.State, HomeContract.Effect>() {

    init {
        getStocks()
    }

    private var searchJob: Job? = null

    override fun initialState() = HomeContract.State()

    override fun handleEvents(event: HomeContract.Event) {
        when (event) {
            is HomeContract.Event.OnStockClicked -> onStockClicked(event.stock)
            is HomeContract.Event.SetTypeOfGraph -> setState {
                copy(
                    typeOfGraph = event.typeOfGraph
                )
            }

            is HomeContract.Event.OnSearch -> {
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500L)
                    getStocks(
                        event.query
                    )
                }
            }
        }
    }

    private fun onStockClicked(stock: TopStock) {

        repository.getStockDescriptionByTicker(ticker = stock.ticker).map { desc ->
            when (desc) {
                is Envelope.Loading -> {
                    setState { copy(isLoading = true) }
                }

                is Envelope.Success -> {
                    getTimeSeries(stock, desc)
                }

                is Envelope.Error -> {
                    setState { copy(isLoading = false) }
                    setEffect { HomeContract.Effect.OnSyncError(desc.error) }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getTimeSeries(
        stock: TopStock,
        desc: Envelope.Success<StockDescription>
    ) = repository.getWeeklyTimeSeries(ticker = stock.ticker).map {
        when (it) {
            is Envelope.Loading -> {

            }

            is Envelope.Success -> {
                setState {
                    copy(
                        isLoading = false,
                        stockDescription = desc.data,
                        stock = stock,
                        points = it.data.weeklyTimeSeries
                    )
                }
                setEffect { HomeContract.Effect.Navigation.NavigateToStockDescription }
            }

            is Envelope.Error -> {
                setState { copy(isLoading = false) }
                setEffect { HomeContract.Effect.OnSyncError(it.error) }
            }
        }
    }.launchIn(viewModelScope)

    fun getStocks(query: String = "") {
        repository.getStocks(query = query).map {
            when (it) {
                is Envelope.Loading -> {
                    setState { copy(isLoading = true) }
                }

                is Envelope.Success -> {
                    setState {
                        copy(
                            isLoading = false,
                            topGainer = it.data.top_gainers,
                            topLoser = it.data.top_losers
                        )
                    }
                }

                is Envelope.Error -> {
                    setState { copy(isLoading = false) }
                    setEffect { HomeContract.Effect.OnSyncError(it.error) }
                }
            }
        }.launchIn(viewModelScope)
    }
}