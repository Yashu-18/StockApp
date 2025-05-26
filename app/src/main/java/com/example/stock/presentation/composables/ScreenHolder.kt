package com.example.stock.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.example.stock.presentation.ui.theme.DarkLightPreviews
import com.example.stock.presentation.ui.theme.StockTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenHolder(
    topBar: @Composable () -> Unit,
    content: @Composable (PaddingValues) -> Unit,
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
    bottomView: @Composable () -> Unit = {},
    snackBarHost: @Composable () -> Unit = {},
    floatingAction: @Composable () -> Unit = {}
) {


    val isVisible = rememberSaveable { mutableStateOf(true) }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                // Hide FAB
                if (available.y < -1) {
                    isVisible.value = false
                }

                // Show FAB
                if (available.y > 1) {
                    isVisible.value = true
                }

                return scrollBehavior.nestedScrollConnection.onPreScroll(available, source)

            }
        }
    }



    Surface(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection)
            .imePadding()
            .navigationBarsPadding()
            .statusBarsPadding()
    ) {
            Scaffold(
                topBar =   {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        topBar()
                    }
                },
                content = content,
                bottomBar =
                {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        bottomView()
                    }
                },
                floatingActionButton = {
                    if (isVisible.value) {
                        floatingAction()
                    }
                },
                snackbarHost = snackBarHost
            )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@DarkLightPreviews
@Composable
fun SetupPinScreenPreview() {
    StockTheme {
        ScreenHolder(
            topBar = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "Top Bar",
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(10.dp)
                    )

                }
            },
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                        .background(Color.Gray),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "Content",
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(10.dp)
                    )

                }
            },

            floatingAction = {
                ExtendedFloatingActionButton(
                    onClick = {},
                    text = { Text("text") },
                    icon = {
                        Icon(
                            imageVector = Icons.Rounded.Add,
                            contentDescription = null
                        )
                    },
                    modifier = Modifier.wrapContentSize()
                )
            },
            bottomView = {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Blue),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "Bottomapp",
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(10.dp)
                    )

                }
            }
        )
    }
}
