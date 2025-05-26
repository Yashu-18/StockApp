package com.example.stock.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.stock.presentation.Screen
import com.example.stock.presentation.screens.launcher.LaunchViewModel
import com.example.stock.presentation.screens.launcher.SplashScreen


@Composable
fun AppNavHost(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            val viewModel = hiltViewModel<LaunchViewModel>()
            viewModel.loadDirection()
            SplashScreen(
                effectFlow = viewModel.effect,
                onLoaded = { effect ->
                    navController.navigate(effect.route){
                        launchSingleTop = true
                        popUpTo(Screen.Splash.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        homeGraph(navController)
    }
}