package br.alisson.edu.tapago.presentation.ui.pay

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.alisson.edu.tapago.data.remote.dto.expenses.GetExpensesResponse
import br.alisson.edu.tapago.data.utils.NetworkResult
import br.alisson.edu.tapago.domain.repository.ExpensesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PayScreenViewModel @Inject constructor(
    private val expensesRepository: ExpensesRepository
) : ViewModel() {

    private val _state = MutableStateFlow(PayScreenState())
    val state = _state.asStateFlow()

    private val _expensesResponse =
        MutableStateFlow<NetworkResult<GetExpensesResponse>>(NetworkResult.Idle)
    val expensesResponse = _expensesResponse.asStateFlow()

    private val _searchQuery = MutableStateFlow("")

    init {
        observeSearchQuery()
    }

    @OptIn(FlowPreview::class)
    private fun observeSearchQuery() {
        viewModelScope.launch {
            _searchQuery
                .debounce(500L)
                .distinctUntilChanged()
                .onEach { query ->
                    _state.update { it.copy(search = query, isSearching = true) }
                }
                .collectLatest {
                    getExpenses(resetPage = true, isRefresh = false)
                    _state.update { it.copy(isSearching = false) }
                }
        }
    }

    fun onEvent(event: PayScreenEvent) {
        when (event) {
            is PayScreenEvent.GetExpenses -> getExpenses(resetPage = true, isRefresh = false)
            is PayScreenEvent.RefreshExpenses -> getExpenses(resetPage = true, isRefresh = true)
            is PayScreenEvent.UpdateSearch -> {
                _searchQuery.value = event.search
                _state.update { it.copy(search = event.search, isSearching = true) }
            }

            is PayScreenEvent.UpdateSort -> {
                _state.value = _state.value.copy(sortBy = event.sortBy, sortOrder = event.sortOrder)
                getExpenses(resetPage = true, isRefresh = false)
            }
        }
    }

    private fun getExpenses(resetPage: Boolean, isRefresh: Boolean) {
        if (_state.value.isLoading && !isRefresh) return

        _state.value = _state.value.copy(
            isLoading = !isRefresh,
            isRefreshing = isRefresh
        )

        viewModelScope.launch {
            try {
                val currentPage = if (resetPage) 1 else _state.value.page
                val response = expensesRepository.getExpenses(
                    page = currentPage,
                    size = _state.value.size,
                    search = _state.value.search,
                    sortBy = _state.value.sortBy,
                    sortOrder = _state.value.sortOrder
                )

                _expensesResponse.value = response
                when (response) {
                    is NetworkResult.Success -> {
                        val newExpenses = response.data.expenses
                        _state.value = _state.value.copy(
                            expenses = if (resetPage) newExpenses else _state.value.expenses + newExpenses,
                            page = currentPage + 1,
                            isLoading = false,
                            isRefreshing = false,
                            isSearching = false
                        )
                    }

                    is NetworkResult.Error -> {
                        _state.value = _state.value.copy(
                            errorMessage = "Erro ao carregar os gastos.",
                            isLoading = false,
                            isRefreshing = false
                        )
                    }

                    else -> {
                        _state.value = _state.value.copy(isLoading = true, isRefreshing = false)
                    }
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    errorMessage = e.message,
                    isLoading = false,
                    isRefreshing = false
                )
            }
        }
    }
}