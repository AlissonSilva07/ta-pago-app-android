package br.alisson.edu.tapago.data.remote.model

import java.io.File

data class SignUpRequest(
    val name: String,
    val email: String,
    val password: String,
    val profilePicture: File
)