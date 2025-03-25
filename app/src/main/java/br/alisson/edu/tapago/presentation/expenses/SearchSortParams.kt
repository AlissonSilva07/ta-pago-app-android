package br.alisson.edu.tapago.presentation.expenses

data class SearchSortParams(
    val search: String? = null,
    val sortBy: String? = null,
    val sortOrder: String? = null
)