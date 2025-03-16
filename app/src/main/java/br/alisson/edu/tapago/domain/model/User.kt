package br.alisson.edu.tapago.domain.model

data class User(
    val createdAt: String,
    val email: String,
    val id: String,
    val name: String,
    val profilePicture: ByteArray
)