package br.alisson.edu.tapago.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.alisson.edu.tapago.data.remote.model.auth.LoginRequest
import br.alisson.edu.tapago.data.remote.model.auth.LoginResponse
import br.alisson.edu.tapago.data.remote.model.auth.SignUpRequest
import br.alisson.edu.tapago.data.remote.model.auth.SignUpResponse
import br.alisson.edu.tapago.data.remote.repository.AuthRepository
import br.alisson.edu.tapago.data.utils.TokenManager
import br.alisson.edu.tapago.presentation.auth.login.LoginEvents
import br.alisson.edu.tapago.presentation.auth.login.LoginState
import br.alisson.edu.tapago.presentation.auth.signup.SignUpEvents
import br.alisson.edu.tapago.presentation.auth.signup.SignUpState
import br.alisson.edu.tapago.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokenManager: TokenManager
) : ViewModel() {

    val authToken: StateFlow<String?> = tokenManager.authToken.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        null
    )

    private val _loginResponse = MutableStateFlow<NetworkResult<LoginResponse>>(NetworkResult.Idle)
    val loginResponse = _loginResponse.asStateFlow()

    private val _signUpResponse = MutableStateFlow<NetworkResult<SignUpResponse>>(NetworkResult.Idle)
    val signUpResponse = _signUpResponse.asStateFlow()

    private val _signUpState = MutableStateFlow(SignUpState())
    val signUpState = _signUpState.asStateFlow()

    private val _loginState = MutableStateFlow(LoginState())
    val loginState = _loginState.asStateFlow()

    private fun validateFields(type: AuthType): Boolean {
        val errors = mutableMapOf<String, String?>()

        when (type) {
            AuthType.LOGIN -> {
                val state = _loginState.value

                if (state.email.isBlank() || state.email.length < 6) {
                    errors["email"] = "O campo deve ter no mínimo 6 caracteres."
                } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(state.email).matches()) {
                    errors["email"] = "O email é inválido."
                }

                if (state.password.isBlank() || state.password.length < 6) {
                    errors["password"] = "O campo deve ter no mínimo 6 caracteres."
                }

                _loginState.update { it.copy(emailError = errors["email"], passwordError = errors["password"]) }
            }

            AuthType.SIGNUP -> {
                val state = _signUpState.value

                if (state.name.isBlank() || state.name.length < 6) {
                    errors["name"] = "O campo deve ter no mínimo 6 caracteres."
                }

                if (state.email.isBlank() || state.email.length < 6) {
                    errors["email"] = "O campo deve ter no mínimo 6 caracteres."
                } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(state.email).matches()) {
                    errors["email"] = "O email é inválido."
                }

                if (state.password.isBlank() || state.password.length < 6) {
                    errors["password"] = "O campo deve ter no mínimo 6 caracteres."
                }

                _signUpState.update {
                    it.copy(
                        nameError = errors["name"],
                        emailError = errors["email"],
                        passwordError = errors["password"]
                    )
                }
            }
        }

        return errors.isEmpty()
    }

    fun onEventLogin(event: LoginEvents) {
        when (event) {
            is LoginEvents.Login -> {
                _loginState.value = _loginState.value.copy(email = event.email, password = event.password)

                if (validateFields(AuthType.LOGIN)) {
                    authRepository.logIn(LoginRequest(email = event.email, password = event.password))
                        .onEach { _loginResponse.value = it }
                        .launchIn(viewModelScope)
                }
            }

            is LoginEvents.UpdateEmail -> _loginState.update { it.copy(email = event.email) }
            is LoginEvents.UpdatePassword -> _loginState.update { it.copy(password = event.password) }
        }
    }


    fun onEventSignUp(event: SignUpEvents) {
        when (event) {
            is SignUpEvents.SignUp -> {
                _signUpState.value = _signUpState.value.copy(
                    name = event.name,
                    email = event.email,
                    password = event.password,
                    profilePicture = event.profilePicture
                )

                if (validateFields(AuthType.SIGNUP)) {
                    authRepository.signUp(
                        SignUpRequest(
                            name = event.name,
                            email = event.email,
                            password = event.password,
                            profilePicture = event.profilePicture!!
                        )
                    )
                        .onEach { _signUpResponse.value = it }
                        .launchIn(viewModelScope)
                }
            }

            is SignUpEvents.UpdateEmail -> _signUpState.update { it.copy(email = event.email) }
            is SignUpEvents.UpdateName -> _signUpState.update { it.copy(name = event.name) }
            is SignUpEvents.UpdatePassword -> _signUpState.update { it.copy(password = event.password) }
            is SignUpEvents.UpdateProfilePicture -> _signUpState.update { it.copy(profilePicture = event.profilePicture) }
        }
    }

}