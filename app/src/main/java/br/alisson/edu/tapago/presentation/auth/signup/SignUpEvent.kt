package br.alisson.edu.tapago.presentation.auth.signup

import java.io.File

sealed class SignUpEvent {
    data class UpdateName(val name: String) : SignUpEvent()
    data class UpdateEmail(val email: String) : SignUpEvent()
    data class UpdatePassword(val password: String) : SignUpEvent()
    data class UpdateProfilePicture(val profilePicture: File?) : SignUpEvent()
    data class SignUp(val name: String, val email: String, val password: String, val profilePicture: File?) : SignUpEvent()
}
