package br.alisson.edu.tapago.data.remote.repository

import android.util.Log
import br.alisson.edu.tapago.data.remote.api.AnalyticsApi
import br.alisson.edu.tapago.data.remote.dto.analytics.MonthlyExpenseProgressResponse
import br.alisson.edu.tapago.data.remote.dto.analytics.TotalExpenseResponse
import br.alisson.edu.tapago.data.remote.dto.user.ExpenseResponse
import br.alisson.edu.tapago.domain.repository.AnalyticsRepository
import br.alisson.edu.tapago.utils.NetworkResult
import javax.inject.Inject

private const val errorMsg = "Algo deu errado."

class AnalyticsRepositoryImpl @Inject constructor (
    private val analyticsApi: AnalyticsApi
) : AnalyticsRepository {
    override suspend fun getSummaryUnpaidExpenses(): NetworkResult<List<ExpenseResponse>> {
        return try {
            val response = analyticsApi.getSummaryUnpaidExpenses()

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

    override suspend fun getTotalExpenses(): NetworkResult<List<TotalExpenseResponse>> {
        return try {
            val response = analyticsApi.getTotalExpenses()

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

    override suspend fun getMonthlyExpenseProgress(): NetworkResult<MonthlyExpenseProgressResponse> {
        return try {
            val response = analyticsApi.getMonthlyExpenseProgress()

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