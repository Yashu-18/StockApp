package com.example.stock.presentation.screens.home.tabLosers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.stock.presentation.composables.StockListItem
import com.example.stock.presentation.screens.home.HomeContract
import com.example.stock.presentation.ui.theme.DarkLightPreviews
import com.example.stock.presentation.ui.theme.StockTheme


@Composable
fun TopLosersScreen(
    state: HomeContract.State,
    onEvent: (event: HomeContract.Event) -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(state.topLoser.size) { item ->
                    val loser = state.topLoser[item]
                    StockListItem(
                        ticker = loser.ticker,
                        price = loser.price,
                        changePercentage = loser.change_percentage
                    ){
                        onEvent(HomeContract.Event.OnStockClicked(loser))
                    }
                }
            }
        }
    }
}


@DarkLightPreviews
@Composable
fun TopLosersPreview() {
    StockTheme {
        TopLosersScreen(
            state = HomeContract.State(),
            onEvent = {}
        )
    }
}