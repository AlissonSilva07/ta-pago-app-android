package br.alisson.edu.tapago.presentation.expenses

import br.alisson.edu.tapago.domain.model.Expense

data class ExpensesState (
    val expenses: List<Expense>? = null,
    var isLoading: Boolean = false,
)