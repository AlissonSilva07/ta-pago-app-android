package br.alisson.edu.tapago.presentation.user

sealed class UserEvent {
    data object GetData : UserEvent()
}
