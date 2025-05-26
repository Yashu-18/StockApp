package com.example.stock.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.example.stock.data.models.timeSeries.WeeklyData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun String.toMarketCapShortForm(): String {
    val number = this.replace(",", "").toDoubleOrNull()
    return when {
        number == null -> "Invalid number"
        number >= 1_000_000_000_000 -> String.format("%.2fT", number / 1_000_000_000_000)
        number >= 1_000_000_000 -> String.format("%.2fB", number / 1_000_000_000)
        number >= 1_000_000 -> String.format("%.2fM", number / 1_000_000)
        number >= 1_000 -> String.format("%.2fK", number / 1_000)
        else -> number.toString()
    }
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navController: NavController): T {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this) { navController.getBackStackEntry(navGraphRoute) }
    return hiltViewModel(parentEntry)
}

@Composable
fun <T> T.useDebounce(
    delayMillis: Long = 300L,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    onChange: (T) -> Unit
): T {
    val state by rememberUpdatedState(this)
    DisposableEffect(state) {
        val job = coroutineScope.launch {
            delay(delayMillis)
            onChange(state)
        }
        onDispose {
            job.cancel()
        }
    }
    return state
}

fun String.toDate(format: String = "yyyy-MM-dd"): Date? {
    return try {
        val dateFormat = SimpleDateFormat(format, Locale.getDefault())
        dateFormat.parse(this)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun List<Pair<String, WeeklyData>>.getCurrentWeekPoints(): List<Pair<String, WeeklyData>> {
    val calendar = Calendar.getInstance()

    // Set the calendar to the start of the current week
    calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
    val startOfWeek = calendar.time

    // Set the calendar to the end of the current week
    calendar.add(Calendar.DAY_OF_WEEK, 6)
    val endOfWeek = calendar.time

    return this.filter {
        val date = it.first.toDate()
        date != null && date >= startOfWeek && date <= endOfWeek
    }
}


fun List<Pair<String, WeeklyData>>.getCurrentMonthPoints(): List<Pair<String, WeeklyData>> {
    val calendar = Calendar.getInstance()

    // Set the calendar to the first day of the current month
    calendar.set(Calendar.DAY_OF_MONTH, 1)
    val startOfMonth = calendar.time

    // Set the calendar to the first day of the next month, then subtract a day to get the last day of the current month
    calendar.add(Calendar.MONTH, 1)
    calendar.set(Calendar.DAY_OF_MONTH, 1)
    calendar.add(Calendar.DAY_OF_MONTH, -1)
    val endOfMonth = calendar.time

    return this.filter {
        val date = it.first.toDate()
        date != null && date >= startOfMonth && date <= endOfMonth
    }
}

fun List<Pair<String, WeeklyData>>.getCurrentYearPoints(): List<Pair<String, WeeklyData>> {
    val calendar = Calendar.getInstance()

    // Set the calendar to the first day of the current year
    calendar.set(Calendar.DAY_OF_YEAR, 1)
    val startOfYear = calendar.time

    // Set the calendar to the last day of the current year
    calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMaximum(Calendar.DAY_OF_YEAR))
    val endOfYear = calendar.time

    return this.filter {
        val date = it.first.toDate()
        date != null && date >= startOfYear && date <= endOfYear
    }
}
