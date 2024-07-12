package com.mostafadevo.todotrackercompose.ui.screens.searchscreen

import com.mostafadevo.todotrackercompose.data.local.Todo

data class SearchScreenUiState(
    val todos: List<Todo>? = emptyList(),
    val query: String = ""
)