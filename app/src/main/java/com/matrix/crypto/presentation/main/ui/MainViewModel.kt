package com.matrix.crypto.presentation.main.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matrix.crypto.data.ItemEntity
import com.matrix.crypto.domain.repository.RetrofitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: RetrofitRepository
) : ViewModel() {

    private val _lastRefreshTime = MutableLiveData<Long>()
    val lastRefreshTime: LiveData<Long> get() = _lastRefreshTime

    // Use MutableLiveData for coinInfoList
    private val _coinInfoList = MutableLiveData<MutableList<ItemEntity>>()

    // Provide LiveData for observing coinInfoList
    val coinInfoList: LiveData<MutableList<ItemEntity>> get() = _coinInfoList

    fun refreshData(apiKey: String) {
        viewModelScope.launch {
            val listApiResponse = repository.getCurrencyDetails(apiKey)
            val liveApiResponse = repository.getExchangeRates(apiKey)
            _lastRefreshTime.value = System.currentTimeMillis()

            val coinInfoList = mutableListOf<ItemEntity>()

            listApiResponse?.crypto?.forEach { (symbol, coinData) ->
                coinInfoList.add(
                    ItemEntity(
                        coinName = coinData.fullName,
                        exchangeRate = liveApiResponse?.rates?.get(symbol) ?: 0.0,
                        iconUrl = coinData.iconUrl
                    )
                )
            }

            // Update _coinInfoList with the new data
            _coinInfoList.value = coinInfoList
        }
    }
}
