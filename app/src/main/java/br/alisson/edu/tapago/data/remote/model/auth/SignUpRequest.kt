package br.alisson.edu.tapago.data.remote.model.auth

import java.io.File

data class SignUpRequest(
    val name: String,
    val email: String,
    val password: String,
    val profilePicture: File
)