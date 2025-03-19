package br.alisson.edu.tapago.data.remote.api

import br.alisson.edu.tapago.data.remote.model.user.UserResponse
import br.alisson.edu.tapago.domain.model.Expense
import retrofit2.Response
import retrofit2.http.GET

interface AnalyticsApi {
    @GET("analytics/unpaid-summary")
    suspend fun getSummaryUnpaidExpenses(): Response<List<Expense>>
}