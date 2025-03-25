package br.alisson.edu.tapago.data.remote.api

import br.alisson.edu.tapago.data.remote.dto.expenses.GetExpensesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ExpensesApi {
    @GET("expenses")
    suspend fun getExpenses(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("search") search: String? = null,
        @Query("sortBy") sortBy: String? = null,
        @Query("sortOrder") sortOrder: String? = null
    ): Response<GetExpensesResponse>
}