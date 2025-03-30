package br.alisson.edu.tapago.presentation.ui.details

import br.alisson.edu.tapago.domain.model.Expense

data class PayItemsDetailsScreenState(
    val expenseById: Expense? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null
)
