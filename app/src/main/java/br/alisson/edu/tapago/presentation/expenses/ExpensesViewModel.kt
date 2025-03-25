package br.alisson.edu.tapago.presentation.expenses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.alisson.edu.tapago.data.remote.dto.expenses.GetExpensesResponse
import br.alisson.edu.tapago.domain.repository.ExpensesRepository
import br.alisson.edu.tapago.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpensesViewModel @Inject constructor(
    private val expensesRepository: ExpensesRepository
): ViewModel() {

    private val _state = MutableStateFlow(ExpensesState())
    val state: StateFlow<ExpensesState> = _state.asStateFlow()

    private val _expensesResponse = MutableStateFlow<NetworkResult<GetExpensesResponse>>(NetworkResult.Idle)
    val expensesResponse = _expensesResponse.asStateFlow()

    fun onEvent(event: ExpensesEvent) {
        when (event) {
            is ExpensesEvent.GetExpenses -> getExpenses()
        }
    }

    private fun getExpenses() {
        viewModelScope.launch {
            _expensesResponse.value = NetworkResult.Loading
            try {
                val response = expensesRepository.getExpenses(
                    page = 1,
                    size = 10,
                    search = null,
                    sortBy = null,
                    sortOrder = null
                )
                when (response) {
                    is NetworkResult.Success -> {
                        _state.value = _state.value.copy(
                            expenses = response.data.expenses
                        )
                    }
                    is NetworkResult.Error -> {
                        _state.value = _state.value.copy(
                            expenses = null,
                            isLoading = false
                        )
                    }

                    else -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }
                }
            } catch (e: Exception) {
                _expensesResponse.value = NetworkResult.Error(e.message ?: "Unknown error")
            }
        }
    }

    init {
        onEvent(ExpensesEvent.GetExpenses)
    }
}