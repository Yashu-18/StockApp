package com.example.stock.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.stock.presentation.Screen
import com.example.stock.presentation.screens.home.description.StockDescriptionScreen
import com.example.stock.presentation.screens.home.HomeContract
import com.example.stock.presentation.screens.home.HomeScreen
import com.example.stock.presentation.screens.home.HomeViewModel
import com.example.stock.presentation.screens.home.tabGainers.TopGainersScreen
import com.example.stock.presentation.screens.home.tabLosers.TopLosersScreen
import com.example.stock.presentation.ui.sharedViewModel

fun NavGraphBuilder.homeGraph(navController: NavHostController) {

    navigation(
        route = Screen.HomeScreen.route,
        startDestination = Screen.HomeScreen.TopGainers.route
    ) {
        composable(Screen.HomeScreen.TopGainers.route) {
            val viewModel = it.sharedViewModel<HomeViewModel>(navController)
            HomeScreen(
                viewModel.viewState.value,
                effectFlow = viewModel.effect,
                onEvent = { event -> viewModel.setEvent(event) },
                onNavigate = { navigationEffect ->
                    when (navigationEffect) {
                        HomeContract.Effect.Navigation.NavigateToStockDescription -> navController.navigate(
                            Screen.HomeScreen.StockDescription.route
                        )
                        else -> {}
                    }
                }
            )
        }

        composable(Screen.HomeScreen.StockDescription.route) {
            val viewModel = it.sharedViewModel<HomeViewModel>(navController)
            StockDescriptionScreen(
                state = viewModel.viewState.value,
                onEvent = { event -> viewModel.setEvent(event) },
                effectFlow = viewModel.effect,
                onNavigate = { navigationEffect ->
                    when (navigationEffect) {
                        HomeContract.Effect.Navigation.NavigateToBack -> navController.navigateUp()
                        else -> {}
                    }
                }
            )
        }
    }
}


object HomeGraph {
    @Composable
    fun HomeBottomNavigation(
        navController: NavHostController,
        state: HomeContract.State,
        onEvent: (event: HomeContract.Event) -> Unit,
    ) {

        NavHost(
            navController = navController,
            startDestination = Screen.HomeScreen.TopGainers.route
        ) {

            composable(Screen.HomeScreen.TopGainers.route) {
                TopGainersScreen(state = state, onEvent = onEvent)
            }

            composable(Screen.HomeScreen.TopLosers.route) {
                TopLosersScreen(state = state, onEvent = onEvent)
            }
        }
    }

}