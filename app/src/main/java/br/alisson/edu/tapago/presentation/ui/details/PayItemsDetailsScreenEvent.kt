package br.alisson.edu.tapago.presentation.ui.details

sealed class PayItemsDetailsScreenEvent {
    data class GetExpenseById(val id: String) : PayItemsDetailsScreenEvent()
    data class DeleteExpenseById(val id: String) : PayItemsDetailsScreenEvent()
    data class PayExpenseById(val id: String) : PayItemsDetailsScreenEvent()
}