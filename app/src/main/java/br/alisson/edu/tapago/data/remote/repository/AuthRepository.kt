package br.alisson.edu.tapago.data.remote.repository

import android.util.Log
import br.alisson.edu.tapago.data.remote.api.AuthApi
import br.alisson.edu.tapago.data.remote.model.LoginRequest
import br.alisson.edu.tapago.data.remote.model.LoginResponse
import br.alisson.edu.tapago.data.remote.model.SignUpRequest
import br.alisson.edu.tapago.data.remote.model.SignUpResponse
import br.alisson.edu.tapago.data.utils.TokenManager
import br.alisson.edu.tapago.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.File

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

    fun signUp(request: SignUpRequest) = flow<NetworkResult<SignUpResponse>> {
        val name = request.name.toRequestBody("text/plain".toMediaTypeOrNull())
        val email = request.email.toRequestBody("text/plain".toMediaTypeOrNull())
        val password = request.password.toRequestBody("text/plain".toMediaTypeOrNull())

        val imageFile = request.profilePicture
        val requestFile = imageFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val profilePicture = MultipartBody.Part.createFormData("profilePicture", imageFile.name, requestFile)

        try {
            val response = authApi.signUp(name, email, password, profilePicture)
            if (response.isSuccessful && response.body() != null) {
                emit(NetworkResult.Success(response.body()!!))
            } else if (response.errorBody() != null) {
                emit(NetworkResult.Error(response.errorBody()!!.charStream().readText()))
            } else {
                emit(NetworkResult.Error(errorMsg))
            }
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message ?: errorMsg))
        }
    }.flowOn(Dispatchers.IO)
        .onStart { emit(NetworkResult.Loading) }

}