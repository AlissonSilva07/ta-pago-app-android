package br.alisson.edu.tapago.presentation.ui.login

sealed class LoginEvent {
    data class UpdateEmail(val email: String) : LoginEvent()
    data class UpdatePassword(val password: String) : LoginEvent()
    data class Login(val email: String, val password: String) : LoginEvent()
}