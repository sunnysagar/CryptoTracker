package com.matrix.crypto.data

data class CoinLayerListApiResponse(
    val success: Boolean,
    val crypto: Map<String, CurrencyDetails>,
    val fiat: Map<String, String>
)
