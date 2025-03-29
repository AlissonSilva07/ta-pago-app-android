package br.alisson.edu.tapago.data.remote.repository

import android.util.Log
import br.alisson.edu.tapago.data.remote.api.UserApi
import br.alisson.edu.tapago.data.remote.dto.user.UserResponse
import br.alisson.edu.tapago.data.utils.UserManager
import br.alisson.edu.tapago.domain.repository.UserRepository
import br.alisson.edu.tapago.core.utils.NetworkResult
import javax.inject.Inject

private const val errorMsg = "Algo deu errado."

class UserRepositoryImpl @Inject constructor(
    private val userApi: UserApi,
    private val userManager: UserManager
): UserRepository {
    override suspend fun getUser(): NetworkResult<UserResponse> {
        return try {
            val response = userApi.getUser()

            if (response.isSuccessful && response.body() != null) {
                val userResponse = response.body()!!
                userManager.updateUserData(response.body()!!.toString())
                NetworkResult.Success(userResponse)

            } else if (response.errorBody() != null) {
                val errorMsg = response.errorBody()!!.charStream().readText()
                NetworkResult.Error(errorMsg)
            } else {
                NetworkResult.Error(errorMsg)
            }
        } catch (e: Exception) {
            Log.e("UserRepository", "Exception: ${e.message}")
            NetworkResult.Error(e.message ?: errorMsg)
        }
    }
}
