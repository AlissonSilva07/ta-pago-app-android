package br.alisson.edu.tapago.data.remote.api

import br.alisson.edu.tapago.data.remote.dto.user.ExpenseResponse
import retrofit2.Response
import retrofit2.http.GET

interface AnalyticsApi {
    @GET("analytics/unpaid-summary")
    suspend fun getSummaryUnpaidExpenses(): Response<List<ExpenseResponse>>
}