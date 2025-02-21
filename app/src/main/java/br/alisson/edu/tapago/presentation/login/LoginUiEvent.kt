package br.alisson.edu.tapago.presentation.login

sealed class LoginUiEvent {
    data class UserNameChanged(val value: String): LoginUiEvent()
    data class PasswordChanged(val value: String): LoginUiEvent()
    object LoginButtonClicked: LoginUiEvent()

}