package com.example.stock.presentation.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.stock.R
import com.example.stock.navigation.HomeGraph.HomeBottomNavigation
import com.example.stock.presentation.base.SIDE_EFFECTS_KEY
import com.example.stock.presentation.composables.bottomMenu.BottomMenuView
import com.example.stock.presentation.composables.bottomMenu.navigateTo
import com.example.stock.presentation.composables.bottomMenu.BottomMenuItem
import com.example.stock.presentation.composables.ProgressDialog
import com.example.stock.presentation.composables.ScreenHolder
import com.example.stock.presentation.composables.ToolbarView
import com.example.stock.presentation.ui.theme.DarkLightPreviews
import com.example.stock.presentation.ui.theme.StockTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: HomeContract.State,
    effectFlow: Flow<HomeContract.Effect>?,
    onEvent: (event: HomeContract.Event) -> Unit,
    onNavigate: (navigationEffect: HomeContract.Effect.Navigation) -> Unit
) {

    val homeNavHostController = rememberNavController()
    val navBackStackEntry by homeNavHostController.currentBackStackEntryAsState()
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val loadingMessage = remember { mutableIntStateOf(R.string.loading) }

    LaunchedEffect(SIDE_EFFECTS_KEY) {
        effectFlow?.onEach { effect ->
            when (effect) {
                is HomeContract.Effect.Navigation -> {
                    onNavigate(effect)
                }

                is HomeContract.Effect.OnSyncError -> {
                    snackbarHostState.showSnackbar(
                        message = effect.error.message.orEmpty(),
                        duration = SnackbarDuration.Short
                    )
                }

                else -> {}
            }
        }?.collect()
    }

    ScreenHolder(
        topBar = {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    ToolbarView(
                        title = "Stock App",
                        isNavEnabled = false,
                        onSearchInput = {
                            onEvent(HomeContract.Event.OnSearch(it))
                        }
                    )
                }
            }
        },
        content = { paddingValues ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {

                HomeBottomNavigation(homeNavHostController, state, onEvent)

            }
        },
        snackBarHost = {
            SnackbarHost(snackbarHostState)
        },
        bottomView = {
            BottomMenuView(
                screens = listOf(
                    BottomMenuItem.TopGainers,
                    BottomMenuItem.TopLosers,
                ),
                onNavigateTo = homeNavHostController::navigateTo,
                currentDestination = navBackStackEntry?.destination
            )
        }
    )
    if (state.isLoading) {
        ProgressDialog(
            message = context.getString(loadingMessage.intValue),
            onDismiss = {}
        )
    }
}


@DarkLightPreviews
@Composable
fun HomeScreenPreview() {
    StockTheme {
        HomeScreen(
            state = HomeContract.State(),
            effectFlow = null,
            onEvent = {},
            onNavigate = {}
        )
    }
}