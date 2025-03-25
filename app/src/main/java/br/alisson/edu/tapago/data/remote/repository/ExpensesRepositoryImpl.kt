package br.alisson.edu.tapago.data.remote.repository

import android.util.Log
import br.alisson.edu.tapago.data.remote.api.ExpensesApi
import br.alisson.edu.tapago.data.remote.dto.expenses.GetExpensesResponse
import br.alisson.edu.tapago.domain.repository.ExpensesRepository
import br.alisson.edu.tapago.utils.NetworkResult
import javax.inject.Inject

private const val errorMsg = "Algo deu errado."

class ExpensesRepositoryImpl @Inject constructor(
    private val expensesApi: ExpensesApi
): ExpensesRepository {

    override suspend fun getExpenses(
        page: Int,
        size: Int,
        search: String?,
        sortBy: String?,
        sortOrder: String?
    ): NetworkResult<GetExpensesResponse> {
        return try {
            val response = expensesApi.getExpenses(page, size, search, sortBy, sortOrder)

            if (response.isSuccessful && response.body() != null) {
                NetworkResult.Success(response.body()!!)
            } else if (response.errorBody() != null) {
                val errorMsg = response.errorBody()!!.charStream().readText()
                NetworkResult.Error(errorMsg)
            } else {
                NetworkResult.Error(errorMsg)
            }
        } catch (e: Exception) {
            Log.e("AnalyticsRepository", "Exception: ${e.message}")
            NetworkResult.Error(e.message ?: errorMsg)
        }
    }
}