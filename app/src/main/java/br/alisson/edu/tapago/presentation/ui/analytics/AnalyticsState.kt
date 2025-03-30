package br.alisson.edu.tapago.presentation.ui.analytics

import br.alisson.edu.tapago.domain.model.Expense
import br.alisson.edu.tapago.domain.model.MonthlyExpenseProgress
import br.alisson.edu.tapago.domain.model.TotalExpense

data class AnalyticsState(
    val summaryUnpaidExpenses: List<Expense>? = null,
    val totalExpensesMonth: List<TotalExpense>? = null,
    val montlhyExpenseProgress: MonthlyExpenseProgress? = null,
    var isLoading: Boolean = false,
)
