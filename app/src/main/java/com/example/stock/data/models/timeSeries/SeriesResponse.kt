package com.example.stock.data.models.timeSeries

import com.google.gson.annotations.SerializedName

data class SeriesResponse(
    @SerializedName("Meta Data")
    val metaData: MetaData,
    @SerializedName("Weekly Time Series")
    val weeklyTimeSeries: Map<String, WeeklyData>
)