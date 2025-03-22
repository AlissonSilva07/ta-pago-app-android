package br.alisson.edu.tapago.presentation.analytics

import br.alisson.edu.tapago.domain.model.Expense
import br.alisson.edu.tapago.domain.model.TotalExpense

data class AnalyticsState(
    val summaryUnpaidExpenses: List<Expense>? = null,
    val totalExpensesMonth: List<TotalExpense>? = null,
    var isLoading: Boolean = false,
)
