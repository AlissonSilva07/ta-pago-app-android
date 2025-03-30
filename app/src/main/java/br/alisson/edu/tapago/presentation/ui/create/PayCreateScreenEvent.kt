package br.alisson.edu.tapago.presentation.ui.create

import br.alisson.edu.tapago.data.remote.dto.expenses.PostExpenseRequest

sealed class PayCreateScreenEvent {
    data class SaveExpense(val expense: PostExpenseRequest) : PayCreateScreenEvent()
    data class UpdateForm(val expense: PostExpenseRequest) : PayCreateScreenEvent()
    data object ResetForm : PayCreateScreenEvent()
}