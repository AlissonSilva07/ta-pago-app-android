package br.alisson.edu.tapago.presentation.expenses

import br.alisson.edu.tapago.data.remote.dto.expenses.PostExpenseRequest
import br.alisson.edu.tapago.domain.model.Expense

data class ExpensesState(
    val expenseById: Expense? = null,
    val expenses: List<Expense> = emptyList(),
    val formExpense: PostExpenseRequest? = PostExpenseRequest(
        title = "",
        amount = 0,
        description = "",
        category = "",
        dueDate = "",
        isPaid = false
    ),
    val page: Int = 1,
    val size: Int = 10,
    val search: String? = null,
    val sortBy: String? = null,
    val sortOrder: String? = null,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null
)