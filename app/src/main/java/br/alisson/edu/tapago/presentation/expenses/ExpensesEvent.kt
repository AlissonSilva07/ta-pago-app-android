package br.alisson.edu.tapago.presentation.expenses

sealed class ExpensesEvent {
    data object GetExpenses : ExpensesEvent()
}