package br.alisson.edu.tapago.presentation.ui.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.alisson.edu.tapago.data.remote.dto.expenses.PostExpenseRequest
import br.alisson.edu.tapago.data.remote.dto.expenses.PostExpenseResponse
import br.alisson.edu.tapago.data.utils.NetworkResult
import br.alisson.edu.tapago.domain.repository.ExpensesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PayCreateScreenViewModel @Inject constructor(
    private val expensesRepository: ExpensesRepository
): ViewModel() {

    private val _state = MutableStateFlow(PayCreateScreenState())
    val state: StateFlow<PayCreateScreenState> = _state.asStateFlow()

    private val _saveExpenseResponse = MutableStateFlow<NetworkResult<PostExpenseResponse>>(
        NetworkResult.Idle
    )
    val saveExpenseResponse = _saveExpenseResponse.asStateFlow()

    fun onEvent(event: PayCreateScreenEvent) {
        when (event) {
            is PayCreateScreenEvent.SaveExpense -> saveExpense(event.expense)
            is PayCreateScreenEvent.UpdateForm -> _state.update { it.copy(formExpense = event.expense) }
            is PayCreateScreenEvent.ResetForm -> _state.update {
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
}