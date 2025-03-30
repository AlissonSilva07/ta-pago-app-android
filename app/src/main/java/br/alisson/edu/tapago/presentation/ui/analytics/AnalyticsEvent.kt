package br.alisson.edu.tapago.presentation.ui.analytics

sealed class AnalyticsEvent {
    data object GetSummaryUnpaidExpenses : AnalyticsEvent()
    data object GetTotalExpenses : AnalyticsEvent()
    data object GetMonthlyExpenseProgress : AnalyticsEvent()
}