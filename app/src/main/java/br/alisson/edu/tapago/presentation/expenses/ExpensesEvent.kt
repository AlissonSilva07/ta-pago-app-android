package br.alisson.edu.tapago.presentation.expenses

sealed class ExpensesEvent {
    data object GetExpenses : ExpensesEvent()
    data class UpdateSearch(val search: String) : ExpensesEvent()
    data class UpdateSort(val sortBy: String, val sortOrder: String) : ExpensesEvent()
}