package br.alisson.edu.tapago.presentation.analytics

sealed class AnalyticsEvent {
    data object GetSummaryUnpaidExpenses : AnalyticsEvent()
    data object GetTotalExpenses : AnalyticsEvent()
}