package com.example.stock.presentation.screens.launcher

import com.example.stock.presentation.base.ViewEvent
import com.example.stock.presentation.base.ViewSideEffect
import com.example.stock.presentation.base.ViewState

class LaunchContract {
    sealed class Event : ViewEvent
    class State : ViewState

    sealed class Effect : ViewSideEffect {
        data class LoadInitData(val route: String) : Effect()
    }

}
