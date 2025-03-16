package br.alisson.edu.tapago.presentation.user

import br.alisson.edu.tapago.domain.model.User

data class UserState(
    val userData: User? = null,
    var isLoading: Boolean = false,
)