package com.mostafadevo.todotrackercompose.ui.screens.homescreen

import com.mostafadevo.todotrackercompose.data.local.Todo

sealed class HomeScreenUiEvent {
    data class ToggleSortingMenu(val isMenuExpaneded: Boolean) : HomeScreenUiEvent()
    data class SortTodos(val sortingOption: SortingOptions) : HomeScreenUiEvent()
    object AddTodo : HomeScreenUiEvent()
    data class onCheckTodo(val todo: Todo, val isComplete: Boolean) : HomeScreenUiEvent()
}