package br.alisson.edu.tapago.domain.repository

import br.alisson.edu.tapago.data.remote.dto.auth.LoginRequest
import br.alisson.edu.tapago.data.remote.dto.auth.LoginResponse
import br.alisson.edu.tapago.data.remote.dto.auth.SignUpRequest
import br.alisson.edu.tapago.data.remote.dto.auth.SignUpResponse
import br.alisson.edu.tapago.utils.NetworkResult

interface AuthRepository {
    suspend fun logIn(request: LoginRequest): NetworkResult<LoginResponse>
    suspend fun signUp(request: SignUpRequest): NetworkResult<SignUpResponse>
}