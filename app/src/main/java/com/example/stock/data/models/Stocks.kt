package com.example.stock.data.models

data class Stocks(
    val last_updated: String,
    val metadata: String,
    val top_gainers: List<TopStock>,
    val top_losers: List<TopStock>
)