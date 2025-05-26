package com.example.stock.presentation.screens.launcher

import androidx.lifecycle.viewModelScope
import com.example.stock.presentation.Screen
import com.example.stock.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaunchViewModel @Inject constructor(
) : BaseViewModel<LaunchContract.Event, LaunchContract.State, LaunchContract.Effect>() {

    override fun initialState() = LaunchContract.State()
    override fun handleEvents(event: LaunchContract.Event) {}

    fun loadDirection(){
        viewModelScope.launch {
            delay(2000)
            setEffect { LaunchContract.Effect.LoadInitData(Screen.HomeScreen.TopGainers.route) }
        }
    }



}