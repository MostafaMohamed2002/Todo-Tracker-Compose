package com.mostafadevo.todotrackercompose.ui.screens.homescreen

import com.mostafadevo.todotrackercompose.data.local.Todo

sealed class HomeScreenUiEvent {
    data class ToggleSortingMenu(val isMenuExpaneded: Boolean) : HomeScreenUiEvent()
    data class SortTodos(val sortingOption: SortingOptions) : HomeScreenUiEvent()
    object AddTodoButton : HomeScreenUiEvent()
    data class onCheckTodo(val currentTodo: Todo, val isComplete: Boolean) : HomeScreenUiEvent()

    data class SelectSegment(val selctedSegment: Int) : HomeScreenUiEvent()

    data class onTodoClick(val todo: Todo) : HomeScreenUiEvent()

    object onDismissTodoDetailesBottomSheet : HomeScreenUiEvent()

    object onDeleteAllTodos : HomeScreenUiEvent()
}