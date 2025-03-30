package br.alisson.edu.tapago.presentation.ui.details

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.alisson.edu.tapago.data.remote.dto.expenses.DeleteExpenseResponse
import br.alisson.edu.tapago.data.remote.dto.expenses.ExpenseResponse
import br.alisson.edu.tapago.data.remote.dto.expenses.PayExpenseResponse
import br.alisson.edu.tapago.data.remote.dto.expenses.toDomainModel
import br.alisson.edu.tapago.data.utils.NetworkResult
import br.alisson.edu.tapago.domain.repository.ExpensesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class PayItemsDetailsScreenViewModel @Inject constructor(
    private val expensesRepository: ExpensesRepository
) : ViewModel() {

    private val _state = MutableStateFlow(PayItemsDetailsScreenState())
    val state: StateFlow<PayItemsDetailsScreenState> = _state.asStateFlow()

    private val _expenseByIdResponse = MutableStateFlow<NetworkResult<ExpenseResponse>>(
        NetworkResult.Idle
    )
    val expenseByIdResponse = _expenseByIdResponse.asStateFlow()

    private val _deleteExpensesByIdResponse =
        MutableStateFlow<NetworkResult<DeleteExpenseResponse>>(
            NetworkResult.Idle
        )
    val deleteExpensesByIdResponse = _deleteExpensesByIdResponse.asStateFlow()

    private val _payExpensesByIdResponse = MutableStateFlow<NetworkResult<PayExpenseResponse>>(
        NetworkResult.Idle
    )
    val payExpensesByIdResponse = _payExpensesByIdResponse.asStateFlow()

    fun onEvent(event: PayItemsDetailsScreenEvent) {
        when (event) {
            is PayItemsDetailsScreenEvent.GetExpenseById -> getExpenseById(event.id)
            is PayItemsDetailsScreenEvent.DeleteExpenseById -> deleteExpenseById(event.id)
            is PayItemsDetailsScreenEvent.PayExpenseById -> payExpenseById(event.id)
        }
    }

    private fun getExpenseById(id: String) {
        if (_state.value.isLoading) return

        _state.value = _state.value.copy(isLoading = true)

        viewModelScope.launch {
            try {
                val response = expensesRepository.getExpensesById(id)

                _expenseByIdResponse.value = response
                when (response) {
                    is NetworkResult.Success -> {
                        val expense = response.data
                        _state.value = _state.value.copy(
                            expenseById = expense.toDomainModel(),
                            isLoading = false
                        )
                    }

                    is NetworkResult.Error -> {
                        _state.value = _state.value.copy(
                            errorMessage = "Erro ao carregar o gasto.",
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

    private fun deleteExpenseById(id: String) {
        if (_state.value.isLoading) return

        _state.value = _state.value.copy(isLoading = true)

        viewModelScope.launch {
            try {
                val response = expensesRepository.deleteExpensesById(id)

                _deleteExpensesByIdResponse.value = response
                when (response) {
                    is NetworkResult.Success -> {
                        _state.value = _state.value.copy(
                            successMessage = response.data.message,
                            isLoading = false
                        )
                    }

                    is NetworkResult.Error -> {
                        _state.value = _state.value.copy(
                            errorMessage = response.msg,
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

    private fun payExpenseById(id: String) {
        if (_state.value.isLoading) return

        _state.value = _state.value.copy(isLoading = true)

        viewModelScope.launch {
            try {
                val response = expensesRepository.payExpensesById(id)

                _payExpensesByIdResponse.value = response
                when (response) {
                    is NetworkResult.Success -> {
                        _state.value = _state.value.copy(
                            successMessage = response.data.message,
                            isLoading = false
                        )
                    }

                    is NetworkResult.Error -> {
                        _state.value = _state.value.copy(
                            errorMessage = response.msg,
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
}