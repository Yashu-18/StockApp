package com.example.stock.presentation

sealed class Screen(val route: String) {

    data object Splash : Screen("splash")

    data object HomeScreen : Screen("home") {
        data object TopGainers : Screen("top_gainers")
        data object TopLosers : Screen("top_losers")
        data object StockDescription : Screen("stock_description")
    }
}