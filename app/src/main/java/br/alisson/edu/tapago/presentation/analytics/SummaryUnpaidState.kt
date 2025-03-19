package br.alisson.edu.tapago.presentation.analytics

import br.alisson.edu.tapago.domain.model.Expense

data class SummaryUnpaidState(
    val summaryUnpaidExpenses: List<Expense>? = null,
    var isLoading: Boolean = false,
)
