package br.alisson.edu.tapago.presentation.ui.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.alisson.edu.tapago.data.remote.dto.auth.SignUpRequest
import br.alisson.edu.tapago.data.remote.dto.auth.SignUpResponse
import br.alisson.edu.tapago.domain.repository.AuthRepository
import br.alisson.edu.tapago.data.utils.AuthValidator
import br.alisson.edu.tapago.data.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {

    private val _state = MutableStateFlow(SignUpState())
    val state: StateFlow<SignUpState> = _state.asStateFlow()

    private val _signUpResponse = MutableStateFlow<NetworkResult<SignUpResponse>>(NetworkResult.Idle)
    val signUpResponse = _signUpResponse.asStateFlow()

    fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.SignUp -> signUp()
            is SignUpEvent.UpdateName -> _state.value = _state.value.copy(name = event.name)
            is SignUpEvent.UpdateEmail -> _state.value = _state.value.copy(email = event.email)
            is SignUpEvent.UpdatePassword -> _state.value = _state.value.copy(password = event.password)
            is SignUpEvent.UpdateProfilePicture -> _state.value = _state.value.copy(profilePicture = event.profilePicture)
        }

    }

    private fun signUp() {
        val errors = AuthValidator.validateSignUpFields(_state.value)

        _state.update {
            it.copy(
                nameError = errors["name"],
                emailError = errors["email"],
                passwordError = errors["password"]
            )
        }

        if (errors.isNotEmpty()) return

        viewModelScope.launch {
            _signUpResponse.value = NetworkResult.Loading
            try {
                val response = authRepository.signUp(
                    SignUpRequest(
                        name = _state.value.name,
                        email = _state.value.email,
                        password = _state.value.password,
                        profilePicture = _state.value.profilePicture ?: return@launch
                    )
                )
                _signUpResponse.value = response
            } catch (e: Exception) {
                _signUpResponse.value = NetworkResult.Error(e.message ?: "Unknown error")
            }
        }
    }
}