package br.alisson.edu.tapago.data.remote.dto.expenses

import br.alisson.edu.tapago.domain.model.Expense

data class GetExpensesResponse(
    val currentPage: Int,
    val expenses: List<Expense>,
    val totalExpenses: Int,
    val totalPages: Int
)