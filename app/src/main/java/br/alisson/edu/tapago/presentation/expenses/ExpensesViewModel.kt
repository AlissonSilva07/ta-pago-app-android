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
            is ExpensesEvent.GetExpenses -> getExpenses(resetPage = true)
            is ExpensesEvent.UpdateSearch -> {
                _state.value = _state.value.copy(search = event.search)
                getExpenses(resetPage = true)
            }
            is ExpensesEvent.UpdateSort -> {
                _state.value = _state.value.copy(sortBy = event.sortBy, sortOrder = event.sortOrder)
                getExpenses(resetPage = true)
            }
        }
    }

    private fun getExpenses(resetPage: Boolean) {
        if (_state.value.isLoading) return

        _state.value = _state.value.copy(isLoading = true)

        viewModelScope.launch {
            try {
                val currentPage = if (resetPage) 1 else _state.value.page
                val response = expensesRepository.getExpenses(
                    page = currentPage,
                    size = _state.value.size,
                    search = _state.value.search,
                    sortBy = _state.value.sortBy,
                    sortOrder = _state.value.sortOrder
                )

                _expensesResponse.value = response
                when (response) {
                    is NetworkResult.Success -> {
                        val newExpenses = response.data.expenses
                        _state.value = _state.value.copy(
                            expenses = if (resetPage) newExpenses else _state.value.expenses + newExpenses,
                            page = currentPage + 1,
                            isLoading = false
                        )
                    }
                    is NetworkResult.Error -> {
                        _state.value = _state.value.copy(
                            errorMessage = "Failed to load expenses",
                            isLoading = false
                        )
                    }

                    else -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    errorMessage = e.message,
                    isLoading = false
                )
            }
        }
    }

    init {
        onEvent(ExpensesEvent.GetExpenses)
    }
}