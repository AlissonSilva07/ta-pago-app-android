package br.alisson.edu.tapago.presentation.ui.expenses

import br.alisson.edu.tapago.data.remote.dto.expenses.PostExpenseRequest

sealed class ExpensesEvent {
    data object GetExpenses : ExpensesEvent()
    data object RefreshExpenses : ExpensesEvent()
    data class SaveExpense(val expense: PostExpenseRequest) : ExpensesEvent()
    data class GetExpenseById(val id: String) : ExpensesEvent()
    data class DeleteExpenseById(val id: String) : ExpensesEvent()
    data class PayExpenseById(val id: String) : ExpensesEvent()
    data class UpdateSearch(val search: String) : ExpensesEvent()
    data class UpdateSort(val sortBy: String, val sortOrder: String) : ExpensesEvent()
    data class UpdateForm(val expense: PostExpenseRequest) : ExpensesEvent()
    data object ResetForm : ExpensesEvent()
}