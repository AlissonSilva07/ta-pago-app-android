package br.alisson.edu.tapago.presentation.ui.pay

sealed class PayScreenEvent {
    data object GetExpenses : PayScreenEvent()
    data object RefreshExpenses : PayScreenEvent()
    data class UpdateSearch(val search: String) : PayScreenEvent()
    data class UpdateSort(val sortBy: String, val sortOrder: String) : PayScreenEvent()
}

