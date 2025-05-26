package com.example.stock.presentation.screens.home.description

import android.graphics.Bitmap
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.stock.R
import com.example.stock.data.models.TopStock
import com.example.stock.data.models.StockDescription
import com.example.stock.presentation.base.SIDE_EFFECTS_KEY
import com.example.stock.presentation.composables.ScreenHolder
import com.example.stock.presentation.composables.StockChart
import com.example.stock.presentation.composables.ToolbarView
import com.example.stock.presentation.screens.home.HomeContract
import com.example.stock.presentation.ui.theme.DarkLightPreviews
import com.example.stock.presentation.ui.theme.StockTheme
import com.example.stock.presentation.ui.theme.green
import com.example.stock.presentation.ui.theme.light_outline
import com.example.stock.presentation.ui.toMarketCapShortForm
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockDescriptionScreen(
    state: HomeContract.State,
    effectFlow: Flow<HomeContract.Effect>?,
    onEvent: (event: HomeContract.Event) -> Unit,
    onNavigate: (navigationEffect: HomeContract.Effect.Navigation) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(SIDE_EFFECTS_KEY) {
        effectFlow?.onEach { effect ->
            when (effect) {
                is HomeContract.Effect.Navigation -> {
                    onNavigate(effect)
                }

                else -> {}
            }
        }?.collect()
    }

    ScreenHolder(
        topBar = {
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                ToolbarView(
                    title = stringResource(R.string.details_screen),
                    onBackPressed = {
                        onNavigate(HomeContract.Effect.Navigation.NavigateToBack)
                    }
                )
            }
        },
        content = { paddingValues ->
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(scrollState)
            ) {
                state.stockDescription?.let { MainHeader(it, state) }
                Graph(state, onEvent)
                state.stockDescription?.let {
                    OverallDescription(
                        it,
                        state.stock ?: TopStock(0, 0, 0, "", "", "", "", "")
                    )
                }
            }

        },
        snackBarHost = {
            SnackbarHost(snackbarHostState)
        }
    )
}

@Composable
private fun Graph(
    state: HomeContract.State,
    onEvent: (event: HomeContract.Event) -> Unit
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(12.dp)
            .border(0.2.dp, MaterialTheme.colorScheme.onSurface, RoundedCornerShape(8.dp)),
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            state.points?.let {
                StockChart(
                    info = it,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .align(Alignment.CenterHorizontally),
                    type = state.typeOfGraph
                )
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .border(0.2.dp, MaterialTheme.colorScheme.onSurface, CircleShape)
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(vertical = 8.dp, horizontal = 16.dp)
                    .height(IntrinsicSize.Max),
            ) {
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(
                            color = if (state.typeOfGraph == 1) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.surface,
                            CircleShape
                        )
                        .clickable {
                            onEvent(HomeContract.Event.SetTypeOfGraph(1))
                        }
                        .padding(16.dp),
                    textAlign = TextAlign.Center,
                    text = stringResource(R.string._1w),
                    style = MaterialTheme.typography.titleSmall,
                    color = if (state.typeOfGraph == 1) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.onSurface
                )
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(
                            color = if (state.typeOfGraph == 2) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.surface,
                            CircleShape
                        )
                        .clickable {
                            onEvent(HomeContract.Event.SetTypeOfGraph(2))
                        }
                        .padding(16.dp),
                    textAlign = TextAlign.Center,
                    text = stringResource(R.string._1m),
                    style = MaterialTheme.typography.titleSmall,
                    color = if (state.typeOfGraph == 2) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.onSurface
                )
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(
                            color = if (state.typeOfGraph == 3) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.surface,
                            CircleShape
                        )
                        .clickable {
                            onEvent(HomeContract.Event.SetTypeOfGraph(3))
                        }
                        .padding(16.dp),
                    textAlign = TextAlign.Center,
                    text = stringResource(R.string._1y),
                    style = MaterialTheme.typography.titleSmall,
                    color = if (state.typeOfGraph == 3) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.onSurface
                )
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(
                            color = if (state.typeOfGraph == 0) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.surface,
                            CircleShape
                        )
                        .clickable {
                            onEvent(HomeContract.Event.SetTypeOfGraph(0))
                        }
                        .padding(16.dp),
                    textAlign = TextAlign.Center,
                    text = stringResource(R.string.all),
                    style = MaterialTheme.typography.titleSmall,
                    color = if (state.typeOfGraph == 0) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.onSurface
                )

            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun OverallDescription(
    stockDescription: StockDescription,
    stock: TopStock
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(0.2.dp, MaterialTheme.colorScheme.onSurface, RoundedCornerShape(8.dp))
            .clickable {

            },
    ) {
        Column(
            modifier = Modifier.background(MaterialTheme.colorScheme.surface)
        ) {
            Text(
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                text = stringResource(R.string.about, stockDescription.Name),
                style = MaterialTheme.typography.titleSmall
            )
            Divider(
                modifier = Modifier.padding(vertical = 4.dp),
                color = MaterialTheme.colorScheme.onSurface,
                thickness = 0.5.dp
            )
            Text(
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                text = stockDescription.Description,
                style = MaterialTheme.typography.bodySmall
            )
            Row(
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 16.dp)
                    .height(IntrinsicSize.Max),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .shadow(0.dp, CircleShape)
                        .background(
                            MaterialTheme.colorScheme.tertiaryContainer,
                            CircleShape
                        )
                        .padding(16.dp),
                    textAlign = TextAlign.Center,
                    text = stringResource(R.string.industry, stockDescription.Industry),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.tertiary
                )
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .shadow(0.dp, CircleShape)
                        .background(
                            MaterialTheme.colorScheme.tertiaryContainer,
                            CircleShape
                        )
                        .padding(16.dp),
                    textAlign = TextAlign.Center,
                    text = stringResource(R.string.sector, stockDescription.Sector),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
            Row(
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 16.dp)
                    .height(IntrinsicSize.Max),
            ) {
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(R.string._52_week_low),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.outline
                    )
                    Text(
                        text = "$${stockDescription.WeekLow52}",
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
                Column(modifier = Modifier.weight(1f)) {
                    StockNumberLine(
                        stockData = stockDescription,
                        gainer = stock
                    )
                }
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(R.string._52_week_high),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.outline
                    )
                    Text(
                        text = "$${stockDescription.WeekHigh52}",
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
            FlowRow(
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = stringResource(R.string.market_cap),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.outline
                    )
                    Text(
                        text = "$${stockDescription.MarketCapitalization.toMarketCapShortForm()}",
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = stringResource(R.string.p_e_ratio),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.outline
                    )
                    Text(
                        text = stockDescription.PERatio,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = stringResource(R.string.beta),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.outline
                    )
                    Text(
                        text = stockDescription.Beta,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = stringResource(R.string.dividend_yield),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.outline
                    )
                    Text(
                        text = "${stockDescription.DividendYield}%",
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = stringResource(R.string.profit_margin),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.outline
                    )
                    Text(
                        text = stockDescription.ProfitMargin,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }
    }
}

@Composable
private fun MainHeader(
    stockDescription: StockDescription,
    state: HomeContract.State
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.surface)
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            modifier = Modifier
                .shadow(1.dp, CircleShape)
                .weight(0.4f)
                .background(
                    MaterialTheme.colorScheme.primaryContainer,
                    CircleShape
                ),
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = null,
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = stockDescription.Name,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "${stockDescription.Symbol}, ${stockDescription.AssetType}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = stockDescription.Exchange,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

        }

        Column(
            modifier = Modifier
        ) {
            Text(
                text = "$ ${state.stock?.price?:"0.0"}",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Row {
                Text(
                    text = state.stock?.price?:"0.0",
                    textAlign = TextAlign.Center,
                    color = if ((state.stock?.price?:"0.0").contains("-")) MaterialTheme.colorScheme.error else green,
                    style = MaterialTheme.typography.bodyMedium,
                )
                Icon(
                    painter = if ((state.stock?.price?:"0.0").contains("-")) painterResource(id = R.drawable.baseline_arrow_drop_down_24) else painterResource(
                        id = R.drawable.baseline_arrow_drop_up_24
                    ),
                    contentDescription = null,
                    tint = if ((state.stock?.price?:"0.0").contains("-")) MaterialTheme.colorScheme.error else green,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}


@Composable
fun StockNumberLine(stockData: StockDescription, gainer: TopStock) {
    val lineColor = MaterialTheme.colorScheme.outlineVariant
    val drawable =
        ContextCompat.getDrawable(LocalContext.current, R.drawable.baseline_arrow_drop_down_24)
    val imageBitmap =
        drawable?.let {
            Bitmap.createBitmap(
                it.intrinsicWidth,
                drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
        }
    val canvas = imageBitmap?.let { android.graphics.Canvas(it) }
    canvas?.let { drawable.setBounds(0, 0, it.width, canvas.height) }
    canvas?.let { drawable.draw(it) }

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(horizontal = 8.dp)
    ) {

        val width = size.width
        val height = size.height / 2
        val low = stockData.WeekLow52
        val high = stockData.WeekHigh52
        val current = gainer.price

        drawLine(
            color = lineColor,
            start = Offset(0f, height),
            end = Offset(width, height),
            strokeWidth = 4f
        )

        val currentPricePosition =
            ((current.toFloat() - low.toFloat()) / (high.toFloat() - low.toFloat())) * width
        drawImage(
            image = imageBitmap!!.asImageBitmap(),
            colorFilter = ColorFilter.tint(color = light_outline),
            topLeft = Offset(x = currentPricePosition, height - 60)
        )
        drawContext.canvas.apply {
            val text = "Current Price: $${gainer.price}"
            val x = currentPricePosition - 90
            val y = height - 60

            val paint = Paint().asFrameworkPaint()
            paint.textSize = 30f
            paint.color = Color.Gray.toArgb()

            drawContext.canvas.nativeCanvas.drawText(text, x, y, paint)
        }
    }
}

@DarkLightPreviews
@Composable
fun StockDescriptionScreenPreview() {
    StockTheme {
        StockDescriptionScreen(
            state = HomeContract.State(),
            effectFlow = null,
            onEvent = {},
            onNavigate = {}
        )
    }
}