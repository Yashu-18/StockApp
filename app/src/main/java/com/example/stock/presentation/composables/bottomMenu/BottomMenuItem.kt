package com.example.stock.presentation.composables.bottomMenu

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.stock.R
import com.example.stock.presentation.Screen

sealed class BottomMenuItem(
    @StringRes val titleRes: Int,
    @DrawableRes val iconRes: Int,
    val route: String
) {

    data object TopGainers : BottomMenuItem(
        route = Screen.HomeScreen.TopGainers.route,
        iconRes = R.drawable.baseline_arrow_drop_up_24,
        titleRes = R.string.top_gainers,
    )

    data object TopLosers : BottomMenuItem(
        route = Screen.HomeScreen.TopLosers.route,
        iconRes = R.drawable.baseline_arrow_drop_down_24,
        titleRes = R.string.top_losers,
    )

}