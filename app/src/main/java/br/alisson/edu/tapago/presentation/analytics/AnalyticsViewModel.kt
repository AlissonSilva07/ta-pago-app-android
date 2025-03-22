package br.alisson.edu.tapago.presentation.analytics

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.alisson.edu.tapago.data.remote.dto.analytics.TotalExpenseResponse
import br.alisson.edu.tapago.data.remote.dto.analytics.toDomainModel
import br.alisson.edu.tapago.data.remote.dto.user.ExpenseResponse
import br.alisson.edu.tapago.data.remote.dto.user.toDomainModel
import br.alisson.edu.tapago.data.remote.repository.AnalyticsRepositoryImpl
import br.alisson.edu.tapago.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class AnalyticsViewModel @Inject constructor(
    private val analyticsRepositoryImpl: AnalyticsRepositoryImpl
): ViewModel() {

    private val _state = MutableStateFlow(AnalyticsState())
    val state = _state.asStateFlow()

    private val _summaryExpensesResponse = MutableStateFlow<NetworkResult<List<ExpenseResponse>>>(NetworkResult.Idle)
    val summaryExpensesResponse = _summaryExpensesResponse.asStateFlow()

    private val _totalExpensesResponse = MutableStateFlow<NetworkResult<List<TotalExpenseResponse>>>(NetworkResult.Idle)
    val totalExpensesResponse = _totalExpensesResponse.asStateFlow()

    fun onEvent(event: AnalyticsEvent) {
        when (event) {
            is AnalyticsEvent.GetSummaryUnpaidExpenses -> getSummaryUnpaidExpenses()
            is AnalyticsEvent.GetTotalExpenses -> getTotalExpenses()
        }
    }

    private fun getSummaryUnpaidExpenses() {
        viewModelScope.launch {
            _summaryExpensesResponse.value = NetworkResult.Loading
            try {
                val response = analyticsRepositoryImpl.getSummaryUnpaidExpenses()
                _summaryExpensesResponse.value = response
                when (response) {
                    is NetworkResult.Success -> {
                        _state.value = _state.value.copy(
                            summaryUnpaidExpenses = response.data.map { it.toDomainModel() }
                        )
                    }
                    is NetworkResult.Error -> {
                        _state.value = _state.value.copy(
                            summaryUnpaidExpenses = null,
                            isLoading = false
                        )
                    }

                    else -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }
                }
                Log.d("UserViewModel", "getUser: $response")
            } catch (e: Exception) {
                _summaryExpensesResponse.value = NetworkResult.Error(e.message ?: "Unknown error")
            }
        }
    }

    private fun getTotalExpenses() {
        viewModelScope.launch {
            _totalExpensesResponse.value = NetworkResult.Loading
            try {
                val response = analyticsRepositoryImpl.getTotalExpenses()
                _totalExpensesResponse.value = response
                when (response) {
                    is NetworkResult.Success -> {
                        _state.value = _state.value.copy(
                            totalExpensesMonth = response.data.map { it.toDomainModel() }
                        )
                    }
                    is NetworkResult.Error -> {
                        _state.value = _state.value.copy(
                            summaryUnpaidExpenses = null,
                            isLoading = false
                        )
                    }

                    else -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }
                }
                Log.d("UserViewModel", "getUser: $response")
            } catch (e: Exception) {
                _summaryExpensesResponse.value = NetworkResult.Error(e.message ?: "Unknown error")
            }
        }
    }
}