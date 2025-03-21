package br.alisson.edu.tapago.domain.repository

import br.alisson.edu.tapago.data.remote.dto.analytics.TotalExpenseResponse
import br.alisson.edu.tapago.data.remote.dto.user.ExpenseResponse
import br.alisson.edu.tapago.utils.NetworkResult

interface AnalyticsRepository {
    suspend fun getSummaryUnpaidExpenses(): NetworkResult<List<ExpenseResponse>>
    suspend fun getTotalExpenses(): NetworkResult<List<TotalExpenseResponse>>
}