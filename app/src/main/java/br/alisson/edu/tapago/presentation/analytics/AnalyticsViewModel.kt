package br.alisson.edu.tapago.presentation.analytics

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.alisson.edu.tapago.data.remote.dto.user.ExpenseResponse
import br.alisson.edu.tapago.data.remote.dto.user.toDomainModel
import br.alisson.edu.tapago.data.remote.repository.AnalyticsRepositoryImpl
import br.alisson.edu.tapago.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class AnalyticsViewModel @Inject constructor(
    private val analyticsRepositoryImpl: AnalyticsRepositoryImpl
): ViewModel() {

    private val _summaryUnpaidExpensesState: MutableStateFlow<SummaryUnpaidState> = MutableStateFlow(SummaryUnpaidState())
    val summaryUnpaidExpensesState = _summaryUnpaidExpensesState.asStateFlow()

    private val _summaryUnpaidExpensesResponse = MutableStateFlow<NetworkResult<List<ExpenseResponse>>>(NetworkResult.Idle)
    val summaryUnpaidExpensesResponse = _summaryUnpaidExpensesResponse.asStateFlow()

    private fun getExpenses() {
        _summaryUnpaidExpensesState.value = _summaryUnpaidExpensesState.value.copy(isLoading = true)

        analyticsRepositoryImpl.getSummaryUnpaidExpenses()
            .onEach { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        _summaryUnpaidExpensesState.value = _summaryUnpaidExpensesState.value.copy(
                            summaryUnpaidExpenses = result.data.map { it.toDomainModel() },
                            isLoading = false
                        )
                    }

                    is NetworkResult.Error -> {
                        _summaryUnpaidExpensesState.value = _summaryUnpaidExpensesState.value.copy(
                            summaryUnpaidExpenses = null,
                            isLoading = false
                        )
                    }

                    else -> {
                        _summaryUnpaidExpensesState.value = _summaryUnpaidExpensesState.value.copy(isLoading = true)
                    }
                }
                _summaryUnpaidExpensesResponse.value = result
            }
            .launchIn(viewModelScope)
    }

    init {
        getExpenses()
    }
}