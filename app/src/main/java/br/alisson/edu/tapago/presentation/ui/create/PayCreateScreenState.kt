package br.alisson.edu.tapago.presentation.ui.create

import br.alisson.edu.tapago.data.remote.dto.expenses.PostExpenseRequest

data class PayCreateScreenState(
    val formExpense: PostExpenseRequest? = PostExpenseRequest(
        title = "",
        amount = 0,
        description = "",
        category = "",
        dueDate = "",
        isPaid = false
    ),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null
)
