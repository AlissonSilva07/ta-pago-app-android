package br.alisson.edu.tapago.domain.repository

import br.alisson.edu.tapago.data.remote.dto.expenses.DeleteExpenseResponse
import br.alisson.edu.tapago.data.remote.dto.expenses.ExpenseResponse
import br.alisson.edu.tapago.data.remote.dto.expenses.GetExpensesResponse
import br.alisson.edu.tapago.utils.NetworkResult

interface ExpensesRepository {
    suspend fun getExpenses(
        page: Int,
        size: Int,
        search: String? = null,
        sortBy: String? = null,
        sortOrder: String? = null
    ): NetworkResult<GetExpensesResponse>

    suspend fun getExpensesById(
        id: String
    ): NetworkResult<ExpenseResponse>

    suspend fun deleteExpensesById(
        id: String
    ): NetworkResult<DeleteExpenseResponse>
}