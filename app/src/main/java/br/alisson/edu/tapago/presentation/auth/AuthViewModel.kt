package br.alisson.edu.tapago.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.alisson.edu.tapago.data.remote.model.LoginRequest
import br.alisson.edu.tapago.data.remote.model.LoginResponse
import br.alisson.edu.tapago.data.remote.repository.AuthRepository
import br.alisson.edu.tapago.data.utils.TokenManager
import br.alisson.edu.tapago.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _userResponse = MutableStateFlow<NetworkResult<LoginResponse>>(NetworkResult.Idle)
    val userResponse = _userResponse.asStateFlow()

    val authToken: StateFlow<String?> = tokenManager.authToken.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        null
    )

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    fun updateEmail(newEmail: String) {
        _email.value = newEmail
    }

    fun updatePassword(newPassword: String) {
        _password.value = newPassword
    }

    fun loginUser(request: LoginRequest) {
        authRepository.logIn(request)
            .onEach { _userResponse.value = it }
            .launchIn(viewModelScope)
    }
}