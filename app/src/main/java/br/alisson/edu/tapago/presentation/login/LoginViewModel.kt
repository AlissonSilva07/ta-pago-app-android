package br.alisson.edu.tapago.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.alisson.edu.tapago.data.remote.auth.AuthRepository
import br.alisson.edu.tapago.data.remote.auth.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository
) :ViewModel() {
    var state by mutableStateOf(LoginState())

    private val resultChannel = Channel<AuthResult<Unit>>()
    val authResults = resultChannel.receiveAsFlow()

    fun onEvent(event: LoginUiEvent) {
        when (event) {
            is LoginUiEvent.UserNameChanged -> {
                state = state.copy(username = event.value)
            }
            is LoginUiEvent.PasswordChanged -> {
                state = state.copy(password = event.value)
            }
            is LoginUiEvent.LoginButtonClicked -> {
                signIn()
            }
        }
    }

    private fun signIn() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = repository.signIn(
                email = state.username,
                password = state.password
            )
            resultChannel.send(result)
            state = state.copy(isLoading = false)
        }
    }
}