package br.alisson.edu.tapago.presentation.ui.signup

import java.io.File

data class SignUpState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val profilePicture: File? = null,
    val nameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null
)