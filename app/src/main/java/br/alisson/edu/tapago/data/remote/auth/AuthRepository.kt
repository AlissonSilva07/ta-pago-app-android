package br.alisson.edu.tapago.data.remote.auth

interface AuthRepository {
    suspend fun signIn(email: String, password: String): AuthResult<Unit>
}