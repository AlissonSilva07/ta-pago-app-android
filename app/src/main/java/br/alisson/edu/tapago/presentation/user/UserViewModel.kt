package br.alisson.edu.tapago.presentation.user

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.alisson.edu.tapago.data.remote.dto.user.UserResponse
import br.alisson.edu.tapago.data.remote.dto.user.toDomainModel
import br.alisson.edu.tapago.data.remote.repository.UserRepositoryImpl
import br.alisson.edu.tapago.data.utils.UserManager
import br.alisson.edu.tapago.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepositoryImpl: UserRepositoryImpl,
    private val userManager: UserManager
) : ViewModel() {

    val userData: StateFlow<String?> = userManager.userData.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        null
    )

    private val _state = MutableStateFlow(UserState())
    val state: StateFlow<UserState> = _state.asStateFlow()

    private val _userResponse = MutableStateFlow<NetworkResult<UserResponse>>(NetworkResult.Idle)
    val userResponse = _userResponse.asStateFlow()

    fun onEvent(event: UserEvent) {
        when (event) {
            is UserEvent.GetData -> getUser()
        }
    }

    private fun getUser() {
        viewModelScope.launch {
            _userResponse.value = NetworkResult.Loading
            try {
                val response = userRepositoryImpl.getUser()
                _userResponse.value = response
                when (response) {
                    is NetworkResult.Success -> {
                        _state.value = _state.value.copy(
                            userData = response.data.toDomainModel()
                        )
                    }
                    is NetworkResult.Error -> {
                        _state.value = _state.value.copy(
                            userData = null,
                            isLoading = false
                        )
                    }

                    else -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }
                }
                Log.d("UserViewModel", "getUser: $response")
            } catch (e: Exception) {
                _userResponse.value = NetworkResult.Error(e.message ?: "Unknown error")
            }
        }
    }
}