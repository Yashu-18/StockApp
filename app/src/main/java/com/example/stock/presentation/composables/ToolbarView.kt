package com.example.stock.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stock.R
import com.example.stock.presentation.ui.theme.DarkLightPreviews
import com.example.stock.presentation.ui.theme.StockTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolbarView(
    title: String,
    isNavEnabled: Boolean = true,
    actions: List<Int>? = null,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    onBackPressed: () -> Unit = {},
    onSearchInput: (String)->Unit = {},
    onActionItemClick: (Int) -> Unit = {},
) {


    Surface(shadowElevation = 1.dp) {
        Row {
            TopAppBar(
                scrollBehavior = scrollBehavior,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary)
                    .weight(0.5f),
                title = {
                    Text(
                        text = title,
                        fontSize = 16.sp,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                navigationIcon = {
                    if (isNavEnabled) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier
                                .clickable(onClick = { onBackPressed() })
                                .padding(horizontal = 12.dp, vertical = 12.dp)
                        )
                    }
                },
                actions = {
                    actions?.forEach { action ->
                        Icon(
                            painter = painterResource(id = action),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .clickable(onClick = { onActionItemClick(action) })
                                .padding(horizontal = 12.dp, vertical = 12.dp)
                        )
                    }
                }
            )
            if(!isNavEnabled){
                Column(modifier = Modifier.weight(1f)
                    .padding(4.dp)) {
                    SearchFilterView(
                        onSearchInput = {
                            onSearchInput(it)
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@DarkLightPreviews
@Composable
fun PreviewMyScreen() {
    StockTheme {
        ToolbarView(
            title = "Test",
        )
    }
}