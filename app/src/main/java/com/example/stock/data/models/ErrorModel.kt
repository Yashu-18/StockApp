package com.example.stock.data.models

import com.google.gson.annotations.SerializedName

data class ErrorModel(
    @SerializedName("message")
    var message: String? = null,
    @SerializedName("mode")
    var mode: Int? = null,
    @SerializedName("error_code")
    var errorCode: Int = 0,
    @SerializedName("can_navigate")
    var canNavigate: Boolean = true,
)