package br.alisson.edu.tapago.data.remote.repository

import android.util.Log
import br.alisson.edu.tapago.data.remote.api.UserApi
import br.alisson.edu.tapago.data.remote.model.user.UserResponse
import br.alisson.edu.tapago.data.utils.UserManager
import br.alisson.edu.tapago.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart

private const val errorMsg = "Algo deu errado."

class UserRepository(
    private val userApi: UserApi,
    private val userManager: UserManager
) {
    fun getUser() = flow<NetworkResult<UserResponse>> {
        try {
            val response = userApi.getUser()

            if (response.isSuccessful && response.body() != null) {
                emit(NetworkResult.Success(response.body()!!))
                userManager.updateUserData(response.body()!!.toString())
            } else if (response.errorBody() != null) {
                val errorMsg = response.errorBody()!!.charStream().readText()
                emit(NetworkResult.Error(errorMsg))
            } else {
                emit(NetworkResult.Error(errorMsg))
            }
        } catch (e: Exception) {
            Log.e("UserRepository", "Exception: ${e.message}")
            emit(NetworkResult.Error(e.message ?: errorMsg))
        }
    }.flowOn(Dispatchers.IO)
        .onStart {
            Log.i("UserRepository", "Loading started")
            emit(NetworkResult.Loading)
        }
}
