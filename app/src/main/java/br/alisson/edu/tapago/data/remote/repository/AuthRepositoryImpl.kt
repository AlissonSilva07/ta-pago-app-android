package br.alisson.edu.tapago.data.remote.repository

import android.util.Log
import br.alisson.edu.tapago.data.remote.api.AuthApi
import br.alisson.edu.tapago.data.remote.dto.auth.LoginRequest
import br.alisson.edu.tapago.data.remote.dto.auth.LoginResponse
import br.alisson.edu.tapago.data.remote.dto.auth.SignUpRequest
import br.alisson.edu.tapago.data.remote.dto.auth.SignUpResponse
import br.alisson.edu.tapago.data.utils.TokenManager
import br.alisson.edu.tapago.domain.repository.AuthRepository
import br.alisson.edu.tapago.data.utils.NetworkResult
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

private const val errorMsg = "Algo deu errado."

class AuthRepositoryImpl @Inject constructor (
    private val authApi: AuthApi,
    private val tokenManager: TokenManager
) : AuthRepository {
    val authToken = tokenManager.authToken

    override suspend fun logIn(request: LoginRequest): NetworkResult<LoginResponse> {
        return try {
            val response = authApi.logIn(request)
            if (response.isSuccessful && response.body() != null) {
                val userResponse = response.body()!!
                tokenManager.updateAuthToken(userResponse.token)
                NetworkResult.Success(userResponse)
            } else if (response.errorBody() != null) {
                val errorMessage = response.errorBody()!!.charStream().readText()
                Log.e("API_RESPONSE", errorMessage)
                NetworkResult.Error(errorMessage)
            } else {
                NetworkResult.Error("An unknown error occurred")
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "An error occurred")
        }
    }

    override suspend fun signUp(request: SignUpRequest): NetworkResult<SignUpResponse> {
        return try {
            val name = request.name.toRequestBody("text/plain".toMediaTypeOrNull())
            val email = request.email.toRequestBody("text/plain".toMediaTypeOrNull())
            val password = request.password.toRequestBody("text/plain".toMediaTypeOrNull())

            val imageFile = request.profilePicture
            val requestFile = imageFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val profilePicture = MultipartBody.Part.createFormData("profilePicture", imageFile.name, requestFile)

            val response = authApi.signUp(name, email, password, profilePicture)

            if (response.isSuccessful && response.body() != null) {
                NetworkResult.Success(response.body()!!)
            } else if (response.errorBody() != null) {
                val errorMessage = response.errorBody()!!.charStream().readText()
                NetworkResult.Error(errorMessage)
            } else {
                NetworkResult.Error("An unknown error occurred")
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "An error occurred")
        }
    }


}