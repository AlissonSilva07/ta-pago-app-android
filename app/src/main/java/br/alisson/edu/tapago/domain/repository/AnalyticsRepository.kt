package br.alisson.edu.tapago.domain.repository

import br.alisson.edu.tapago.data.remote.dto.analytics.MonthlyExpenseProgressResponse
import br.alisson.edu.tapago.data.remote.dto.analytics.TotalExpenseResponse
import br.alisson.edu.tapago.data.remote.dto.expenses.ExpenseResponse
import br.alisson.edu.tapago.utils.NetworkResult

interface AnalyticsRepository {
    suspend fun getSummaryUnpaidExpenses(): NetworkResult<List<ExpenseResponse>>
    suspend fun getTotalExpenses(): NetworkResult<List<TotalExpenseResponse>>
    suspend fun getMonthlyExpenseProgress(): NetworkResult<MonthlyExpenseProgressResponse>
}