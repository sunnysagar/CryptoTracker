package com.matrix.crypto.domain

import com.matrix.crypto.data.CoinLayerListApiResponse
import com.matrix.crypto.data.CoinLayerLiveApiResponse
import retrofit2.http.Query

import retrofit2.Response
import retrofit2.http.GET


interface ApiService {

    @GET("live")
    suspend fun getExchangeRates(@Query("access_key") apiKey: String): Response<CoinLayerLiveApiResponse>

    @GET("list")
    suspend fun getCurrencyDetails(@Query("access_key") apiKey: String): Response<CoinLayerListApiResponse>
}