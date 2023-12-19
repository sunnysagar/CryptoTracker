package com.matrix.crypto.domain.repository


import android.util.Log
import com.matrix.crypto.data.CoinLayerListApiResponse
import com.matrix.crypto.data.CoinLayerLiveApiResponse
import com.matrix.crypto.domain.ApiService
import javax.inject.Inject

class RetrofitRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getExchangeRates(apiKey: String): CoinLayerLiveApiResponse? {
        Log.d("API RESPONSES::", "Currency Details Response")
        return apiService.getExchangeRates(apiKey).body()
    }

    suspend fun getCurrencyDetails(apiKey: String): CoinLayerListApiResponse? {
        return apiService.getCurrencyDetails(apiKey).body()
    }

}