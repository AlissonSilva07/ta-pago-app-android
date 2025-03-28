package br.alisson.edu.tapago.presentation.expenses

sealed class ExpensesEvent {
    data object GetExpenses : ExpensesEvent()
    data class GetExpenseById(val id: String) : ExpensesEvent()
    data class DeleteExpenseById(val id: String) : ExpensesEvent()
    data class UpdateSearch(val search: String) : ExpensesEvent()
    data class UpdateSort(val sortBy: String, val sortOrder: String) : ExpensesEvent()
}