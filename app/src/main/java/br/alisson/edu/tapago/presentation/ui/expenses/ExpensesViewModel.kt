package br.alisson.edu.tapago.presentation.ui.expenses

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.alisson.edu.tapago.data.utils.NetworkResult
import br.alisson.edu.tapago.data.remote.dto.expenses.DeleteExpenseResponse
import br.alisson.edu.tapago.data.remote.dto.expenses.ExpenseResponse
import br.alisson.edu.tapago.data.remote.dto.expenses.GetExpensesResponse
import br.alisson.edu.tapago.data.remote.dto.expenses.PayExpenseResponse
import br.alisson.edu.tapago.data.remote.dto.expenses.PostExpenseRequest
import br.alisson.edu.tapago.data.remote.dto.expenses.PostExpenseResponse
import br.alisson.edu.tapago.data.remote.dto.expenses.toDomainModel
import br.alisson.edu.tapago.domain.repository.ExpensesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class ExpensesViewModel @Inject constructor(
    private val expensesRepository: ExpensesRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ExpensesState())
    val state: StateFlow<ExpensesState> = _state.asStateFlow()

    private val _expensesResponse = MutableStateFlow<NetworkResult<GetExpensesResponse>>(
        NetworkResult.Idle
    )
    val expensesResponse = _expensesResponse.asStateFlow()

    private val _saveExpenseResponse = MutableStateFlow<NetworkResult<PostExpenseResponse>>(
        NetworkResult.Idle
    )
    val saveExpenseResponse = _saveExpenseResponse.asStateFlow()

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

    fun onEvent(event: ExpensesEvent) {
        when (event) {
            is ExpensesEvent.GetExpenses -> getExpenses(resetPage = true, isRefresh = false)
            is ExpensesEvent.RefreshExpenses -> getExpenses(resetPage = true, isRefresh = true)
            is ExpensesEvent.UpdateSearch -> {
                _state.value = _state.value.copy(search = event.search)
                getExpenses(resetPage = true, isRefresh = false)
            }
            is ExpensesEvent.UpdateSort -> {
                _state.value = _state.value.copy(sortBy = event.sortBy, sortOrder = event.sortOrder)
                getExpenses(resetPage = true, isRefresh = false)
            }
            is ExpensesEvent.GetExpenseById -> getExpenseById(event.id)
            is ExpensesEvent.DeleteExpenseById -> deleteExpenseById(event.id)
            is ExpensesEvent.PayExpenseById -> payExpenseById(event.id)
            is ExpensesEvent.SaveExpense -> saveExpense(event.expense)
            is ExpensesEvent.UpdateForm -> _state.update { it.copy(formExpense = event.expense) }
            is ExpensesEvent.ResetForm -> _state.update {
                it.copy(
                    formExpense = PostExpenseRequest(
                        title = "",
                        description = "",
                        amount = 0,
                        category = "",
                        dueDate = "",
                        isPaid = false
                    )
                )
            }
        }
    }

    private fun getExpenses(resetPage: Boolean, isRefresh: Boolean) {
        if (_state.value.isLoading && !isRefresh) return

        _state.value = _state.value.copy(
            isLoading = !isRefresh,
            isRefreshing = isRefresh
        )

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
                            isLoading = false,
                            isRefreshing = false
                        )
                    }

                    is NetworkResult.Error -> {
                        _state.value = _state.value.copy(
                            errorMessage = "Erro ao carregar os gastos.",
                            isLoading = false,
                            isRefreshing = false
                        )
                    }

                    else -> {
                        _state.value = _state.value.copy(isLoading = true, isRefreshing = false)
                    }
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    errorMessage = e.message,
                    isLoading = false,
                    isRefreshing = false
                )
            }
        }
    }

    private fun saveExpense(expense: PostExpenseRequest) {
        if (_state.value.isLoading) return

        _state.value = _state.value.copy(isLoading = true)

        viewModelScope.launch {
            try {
                val response = expensesRepository.saveExpense(expense)

                _saveExpenseResponse.value = response
                when (response) {
                    is NetworkResult.Success -> {
                        val expense = response.data
                        _state.value = _state.value.copy(
                            successMessage = "Sucesso ao salvar o gasto com id ${expense.expenseId}",
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