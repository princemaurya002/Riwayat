package com.princemaurya.rewayat000.ui.operations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.princemaurya.rewayat000.data.repository.BusinessAnalyticsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OperationsViewModel @Inject constructor(
    private val analyticsRepository: BusinessAnalyticsRepository
) : ViewModel() {

    private val _forecast = MutableLiveData<String>()
    val forecast: LiveData<String> = _forecast

    private val _taxSummary = MutableLiveData<String>()
    val taxSummary: LiveData<String> = _taxSummary

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun loadInsights() {
        viewModelScope.launch {
            _isLoading.value = true
            
            // Parallel execution for better UX
            launch {
                analyticsRepository.getSalesForecast()
                    .onSuccess { _forecast.value = it }
                    .onFailure { _error.value = it.message ?: "Forecast failed" }
            }

            launch {
                analyticsRepository.getTaxSummary()
                    .onSuccess { _taxSummary.value = it }
                    .onFailure { _error.value = it.message ?: "Tax summary failed" }
            }

            _isLoading.value = false
        }
    }
}
