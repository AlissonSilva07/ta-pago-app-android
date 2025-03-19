package br.alisson.edu.tapago.data.remote.repository

import android.util.Log
import br.alisson.edu.tapago.data.remote.api.AnalyticsApi
import br.alisson.edu.tapago.domain.model.Expense
import br.alisson.edu.tapago.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart

private const val errorMsg = "Algo deu errado."

class AnalyticsRepository(
    private val analyticsApi: AnalyticsApi
) {
    fun getSummaryUnpaidExpenses() = flow<NetworkResult<List<Expense>>> {
        try {
            val response = analyticsApi.getSummaryUnpaidExpenses()

            if (response.isSuccessful && response.body() != null) {
                emit(NetworkResult.Success(response.body()!!))
            } else if (response.errorBody() != null) {
                val errorMsg = response.errorBody()!!.charStream().readText()
                emit(NetworkResult.Error(errorMsg))
            } else {
                emit(NetworkResult.Error(errorMsg))
            }
        } catch (e: Exception) {
            Log.e("AnalyticsRepository", "Exception: ${e.message}")
            emit(NetworkResult.Error(e.message ?: errorMsg))
        }
    }.flowOn(Dispatchers.IO)
        .onStart {
            Log.i("AnalyticsRepository", "Loading started")
            emit(NetworkResult.Loading)
        }
}