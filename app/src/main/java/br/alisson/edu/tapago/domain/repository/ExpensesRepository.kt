package br.alisson.edu.tapago.domain.repository

import br.alisson.edu.tapago.data.remote.dto.expenses.DeleteExpenseResponse
import br.alisson.edu.tapago.data.remote.dto.expenses.ExpenseResponse
import br.alisson.edu.tapago.data.remote.dto.expenses.GetExpensesResponse
import br.alisson.edu.tapago.data.remote.dto.expenses.PayExpenseResponse
import br.alisson.edu.tapago.data.remote.dto.expenses.PostExpenseRequest
import br.alisson.edu.tapago.data.remote.dto.expenses.PostExpenseResponse
import br.alisson.edu.tapago.data.utils.NetworkResult

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

    suspend fun saveExpense(
        expense: PostExpenseRequest
    ): NetworkResult<PostExpenseResponse>

    suspend fun deleteExpensesById(
        id: String
    ): NetworkResult<DeleteExpenseResponse>

    suspend fun payExpensesById(
        id: String
    ): NetworkResult<PayExpenseResponse>
}