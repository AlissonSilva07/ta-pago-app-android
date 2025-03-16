package br.alisson.edu.tapago.presentation.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.alisson.edu.tapago.data.remote.model.user.UserResponse
import br.alisson.edu.tapago.data.remote.model.user.toDomainModel
import br.alisson.edu.tapago.data.remote.repository.UserRepository
import br.alisson.edu.tapago.data.utils.UserManager
import br.alisson.edu.tapago.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val userManager: UserManager
) : ViewModel() {

    val userData: StateFlow<String?> = userManager.userData.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        null
    )

    private val _userState: MutableStateFlow<UserState> = MutableStateFlow(UserState())
    val userState = _userState.asStateFlow()

    private val _userResponse = MutableStateFlow<NetworkResult<UserResponse>>(NetworkResult.Idle)
    val userResponse = _userResponse.asStateFlow()

    private fun getUserData() {
        _userState.value = _userState.value.copy(isLoading = true)

        userRepository.getUser()
            .onEach { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        _userState.value = _userState.value.copy(
                            userData = result.data.toDomainModel(),
                            isLoading = false
                        )
                    }

                    is NetworkResult.Error -> {
                        _userState.value = _userState.value.copy(
                            userData = null,
                            isLoading = false
                        )
                    }

                    else -> {
                        _userState.value = _userState.value.copy(isLoading = true)
                    }
                }
                _userResponse.value = result
            }
            .launchIn(viewModelScope)
    }

    init {
        getUserData()
    }
}