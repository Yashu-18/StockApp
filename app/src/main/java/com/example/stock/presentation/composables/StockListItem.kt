package com.example.stock.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.stock.R
import com.example.stock.presentation.ui.theme.DarkLightPreviews
import com.example.stock.presentation.ui.theme.StockTheme
import com.example.stock.presentation.ui.theme.green


@Composable
fun StockListItem(
    ticker: String,
    price: String,
    changePercentage: String,
    onClick:()->Unit
) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier.clickable {
            onClick()
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.large)
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Image(
                modifier = Modifier
                    .shadow(1.dp, CircleShape)
                    .background(
                        MaterialTheme.colorScheme.primaryContainer,
                        CircleShape
                    ),
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = null,
            )

            Text(
                text = ticker,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = "$ ${price}",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Row {
                Text(
                    text = changePercentage,
                    textAlign = TextAlign.Center,
                    color = if (changePercentage.contains("-")) MaterialTheme.colorScheme.error else green,
                    style = MaterialTheme.typography.bodyMedium,
                )
                Icon(
                    painter = if (changePercentage.contains("-")) painterResource(id = R.drawable.baseline_arrow_drop_down_24) else painterResource(
                        id = R.drawable.baseline_arrow_drop_up_24
                    ),
                    contentDescription = null,
                    tint = if (changePercentage.contains("-")) MaterialTheme.colorScheme.error else green,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@DarkLightPreviews
@Composable
fun StockListItemPreview() {
    StockTheme {
        StockListItem(
            ticker = "GOVXW",
            price = "0.17",
            changePercentage = "150.3682%"
        ){

        }
    }
}