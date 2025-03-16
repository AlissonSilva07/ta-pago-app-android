package br.alisson.edu.tapago.presentation.auth.signup

import java.io.File

sealed class SignUpEvents {
    data class UpdateName(val name: String) : SignUpEvents()
    data class UpdateEmail(val email: String) : SignUpEvents()
    data class UpdatePassword(val password: String) : SignUpEvents()
    data class UpdateProfilePicture(val profilePicture: File?) : SignUpEvents()
    data class SignUp(val name: String, val email: String, val password: String, val profilePicture: File?) : SignUpEvents()
}
