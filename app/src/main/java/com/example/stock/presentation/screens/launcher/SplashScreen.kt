package com.example.stock.presentation.screens.launcher

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stock.R
import com.example.stock.presentation.composables.ScreenHolder
import com.example.stock.presentation.ui.theme.DarkLightPreviews
import com.example.stock.presentation.ui.theme.StockTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SplashScreen(
    effectFlow: Flow<LaunchContract.Effect>?,
    onLoaded: (navigationEffect: LaunchContract.Effect.LoadInitData) -> Unit
) {

    LaunchedEffect(Unit) {
        effectFlow?.onEach { effect ->
            when (effect) {
                is LaunchContract.Effect.LoadInitData -> onLoaded(effect)
            }
        }?.collect()
    }

    ScreenHolder(
        topBar = {},
        bottomView = {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                text = stringResource(id = R.string.loading),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.Center,
                fontSize = 12.sp
            )
        },
        content ={
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(it),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter =  painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = null,
                    modifier = Modifier
                        .size(120.dp)
                        .background(MaterialTheme.colorScheme.secondary, RoundedCornerShape(16.dp)),
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.stock_edge),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                )
            }
        }
    )

}


@DarkLightPreviews
@Composable
fun SplashScreenPreview() {
    StockTheme {
        SplashScreen(
            effectFlow = null
        ){

        }
    }
}