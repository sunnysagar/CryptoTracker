package com.matrix.crypto.data

import com.google.gson.annotations.SerializedName

data class CurrencyDetails(
    val symbol: String,
    val name: String,
    @SerializedName("name_full") val fullName: String,
    @SerializedName("max_supply") val maxSupply: Any,
    @SerializedName("icon_url") val iconUrl: String
)