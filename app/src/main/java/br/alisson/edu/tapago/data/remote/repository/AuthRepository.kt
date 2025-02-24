package br.alisson.edu.tapago.data.remote.repository

import android.util.Log
import br.alisson.edu.tapago.data.remote.api.AuthApi
import br.alisson.edu.tapago.data.remote.model.LoginRequest
import br.alisson.edu.tapago.data.remote.model.LoginResponse
import br.alisson.edu.tapago.data.utils.TokenManager
import br.alisson.edu.tapago.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import retrofit2.Response

private const val errorMsg = "Algo deu errado."

class AuthRepository(
    private val authApi: AuthApi,
    private val tokenManager: TokenManager
) {
    val authToken = tokenManager.authToken

    fun logIn(request: LoginRequest) = doAuth { authApi.logIn(request) }

    private inline fun doAuth(
        crossinline authenticate: suspend () -> Response<LoginResponse>,
    ) = flow<NetworkResult<LoginResponse>> {
        try {
            val response = authenticate()
            if (response.isSuccessful && response.body() != null) {
                val userResponse = response.body()!!
                tokenManager.updateAuthToken(userResponse.token)
                emit(NetworkResult.Success(response.body()!!))
            } else if (response.errorBody() != null) {
                emit(NetworkResult.Error(response.errorBody()!!.charStream().readText()))
                Log.e("API_RESPONSE", response.errorBody()!!.charStream().readText())
            } else {
                emit(NetworkResult.Error(errorMsg))
            }
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message ?: errorMsg))
        }
    }.flowOn(Dispatchers.IO)
        .onStart { emit(NetworkResult.Loading) }

}