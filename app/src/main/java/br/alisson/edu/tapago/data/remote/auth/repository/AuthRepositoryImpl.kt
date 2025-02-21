package br.alisson.edu.tapago.data.remote.auth.repository

import android.content.SharedPreferences
import br.alisson.edu.tapago.data.remote.auth.AuthApi
import br.alisson.edu.tapago.data.remote.auth.AuthRepository
import br.alisson.edu.tapago.data.remote.auth.AuthResult
import br.alisson.edu.tapago.data.remote.auth.LoginRequest
import coil.network.HttpException

class AuthRepositoryImpl(
    private val authApi: AuthApi,
    private val prefs: SharedPreferences
): AuthRepository {
    override suspend fun signIn(email: String, password: String): AuthResult<Unit> {
        return try {
            val response = authApi.signIn(
                request = LoginRequest(
                    email = email,
                    password = password
                )
            )
            prefs.edit()
                .putString("jwt", response.token)
                .apply()
            AuthResult.Authorized()
        } catch (e: HttpException) {
            if (e.response.code == 401) {
                AuthResult.Unauthorized()
            } else {
                AuthResult.UnknownError()
            }
        }
    }

}