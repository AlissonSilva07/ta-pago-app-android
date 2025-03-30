package br.alisson.edu.tapago.presentation.ui.user

sealed class UserEvent {
    data object GetData : UserEvent()
}
