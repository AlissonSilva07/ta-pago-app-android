package br.alisson.edu.tapago.core.utils

import android.util.Patterns
import br.alisson.edu.tapago.presentation.auth.login.LoginState
import br.alisson.edu.tapago.presentation.auth.signup.SignUpState

object AuthValidator {

    fun validateLoginFields(state: LoginState): Map<String, String?> {
        val errors = mutableMapOf<String, String?>()

        if (state.email.isBlank() || state.email.length < 6) {
            errors["email"] = "O campo deve ter no mínimo 6 caracteres."
        } else if (!Patterns.EMAIL_ADDRESS.matcher(state.email).matches()) {
            errors["email"] = "O email é inválido."
        }

        if (state.password.isBlank() || state.password.length < 6) {
            errors["password"] = "O campo deve ter no mínimo 6 caracteres."
        }

        return errors
    }

    fun validateSignUpFields(state: SignUpState): Map<String, String?> {
        val errors = mutableMapOf<String, String?>()

        if (state.name.isBlank() || state.name.length < 6) {
            errors["name"] = "O campo deve ter no mínimo 6 caracteres."
        }

        if (state.email.isBlank() || state.email.length < 6) {
            errors["email"] = "O campo deve ter no mínimo 6 caracteres."
        } else if (!Patterns.EMAIL_ADDRESS.matcher(state.email).matches()) {
            errors["email"] = "O email é inválido."
        }

        if (state.password.isBlank() || state.password.length < 6) {
            errors["password"] = "O campo deve ter no mínimo 6 caracteres."
        }

        return errors
    }
}
