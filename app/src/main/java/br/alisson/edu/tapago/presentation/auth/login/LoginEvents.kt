package br.alisson.edu.tapago.presentation.auth.login

sealed class LoginEvents {
    data class UpdateEmail(val email: String) : LoginEvents()
    data class UpdatePassword(val password: String) : LoginEvents()
    data class Login(val email: String, val password: String) : LoginEvents()
}