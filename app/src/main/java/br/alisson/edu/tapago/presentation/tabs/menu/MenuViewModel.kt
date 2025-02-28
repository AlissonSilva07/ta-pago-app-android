package br.alisson.edu.tapago.presentation.tabs.menu

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.alisson.edu.tapago.data.utils.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val tokenManager: TokenManager
): ViewModel() {

    fun logOut(activity: Activity) {
        viewModelScope.launch {
            tokenManager.deleteAuthToken()
        }
    }
}