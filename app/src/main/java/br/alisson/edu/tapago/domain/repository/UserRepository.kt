package br.alisson.edu.tapago.domain.repository

import br.alisson.edu.tapago.data.remote.dto.user.UserResponse
import br.alisson.edu.tapago.data.utils.NetworkResult

interface UserRepository {
    suspend fun getUser(): NetworkResult<UserResponse>
}