package com.matrix.crypto.data

import com.google.gson.annotations.SerializedName

data class CoinLayerLiveApiResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("terms")
    val terms: String,
    @SerializedName("privacy")
    val privacy: String,
    @SerializedName("timestamp")
    val timestamp: Long,
    @SerializedName("target")
    val target: String,
    @SerializedName("rates")
    val rates: Map<String, Double>
)
