package com.example.stock.presentation.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stock.R
import com.example.stock.presentation.ui.theme.DarkLightPreviews
import com.example.stock.presentation.ui.theme.StockTheme
import com.example.stock.presentation.ui.useDebounce

@Composable
fun SearchFilterView(
    onSearchInput: (String) -> Unit = {},
    isReadOnly: Boolean = false,
    hint: Int = R.string.search_stock_by_ticker,
    borderColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
) {

    var inputValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }

    inputValue.useDebounce {
        onSearchInput(it.text)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        TextField(
            modifier = Modifier
                .weight(1f)
                .height(50.dp)
//                .clickable(
//                    onClick = onSearchClick
//                )
                .padding(end = 8.dp, bottom = 0.dp, top = 0.dp)
                .border(
                    width = 1.dp,
                    color = borderColor,
                    shape = RoundedCornerShape(8.dp)
                ),
            value = inputValue,
            singleLine = true,
            enabled = true,
            readOnly = isReadOnly,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            ),
            textStyle = TextStyle(fontSize = 14.sp),
            placeholder = {
                Text(
                    text = stringResource(id = hint),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 14.sp
                )
            }, leadingIcon = {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.Rounded.Search,
                    tint = MaterialTheme.colorScheme.onSurface,
                    contentDescription = null
                )
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                cursorColor = Color.Black,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
            ),
            onValueChange = {
                inputValue = it
            }
        )
    }

}


@DarkLightPreviews
@Composable
fun SearchFilterViewPreview() {
    StockTheme {
        SearchFilterView(
            onSearchInput = {},
        )
    }

}