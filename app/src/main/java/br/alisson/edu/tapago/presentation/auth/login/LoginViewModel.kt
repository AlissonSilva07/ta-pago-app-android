package br.alisson.edu.tapago.presentation.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.alisson.edu.tapago.data.remote.dto.auth.LoginRequest
import br.alisson.edu.tapago.data.remote.dto.auth.LoginResponse
import br.alisson.edu.tapago.data.utils.TokenManager
import br.alisson.edu.tapago.domain.repository.AuthRepository
import br.alisson.edu.tapago.core.utils.AuthValidator
import br.alisson.edu.tapago.core.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    tokenManager: TokenManager

) : ViewModel() {

    val authToken: StateFlow<String?> = tokenManager.authToken.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        null
    )

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    private val _loginResponse = MutableStateFlow<NetworkResult<LoginResponse>>(NetworkResult.Idle)
    val loginResponse = _loginResponse.asStateFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.Login -> logIn()
            is LoginEvent.UpdateEmail -> _state.update { it.copy(email = event.email) }
            is LoginEvent.UpdatePassword -> _state.update { it.copy(password = event.password) }
        }
    }

    private fun logIn() {
        val errors = AuthValidator.validateLoginFields(_state.value)

        _state.update {
            it.copy(emailError = errors["email"], passwordError = errors["password"])
        }

        if (errors.isNotEmpty()) return

        viewModelScope.launch {
            _loginResponse.value = NetworkResult.Loading
            try {
                val response = authRepository.logIn(
                    LoginRequest(email = _state.value.email, password = _state.value.password)
                )
                _loginResponse.value = response
            } catch (e: Exception) {
                _loginResponse.value = NetworkResult.Error(e.message ?: "Unknown error")
            }
        }
    }

}