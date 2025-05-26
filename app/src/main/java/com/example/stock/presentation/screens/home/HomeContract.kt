package com.example.stock.presentation.screens.home

import com.example.stock.data.models.ErrorModel
import com.example.stock.data.models.TopStock
import com.example.stock.data.models.StockDescription
import com.example.stock.data.models.timeSeries.WeeklyData
import com.example.stock.presentation.base.ViewEvent
import com.example.stock.presentation.base.ViewSideEffect
import com.example.stock.presentation.base.ViewState

class HomeContract {
    sealed class Event : ViewEvent {
        class OnStockClicked(val stock: TopStock): Event()
        class SetTypeOfGraph(val typeOfGraph: Int): Event()
        data class OnSearch(val query: String) : Event()
    }

    data class State(
        val topGainer: List<TopStock> = listOf(),
        val topLoser: List<TopStock> = listOf(),
        val stock: TopStock? = null,
        val isLoading: Boolean = false,
        val stockDescription: StockDescription? = null,
        val points: Map<String, WeeklyData>? = null,
        val typeOfGraph:Int = 0,
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        data class OnSyncError(val error: ErrorModel) : Effect()

        sealed class Navigation : Effect() {
            data object NavigateToStockDescription: Navigation()
            data object NavigateToBack: Navigation()
        }
    }
}