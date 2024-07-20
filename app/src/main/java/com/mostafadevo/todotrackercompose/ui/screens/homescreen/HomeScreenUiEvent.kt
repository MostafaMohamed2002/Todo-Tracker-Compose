package com.mostafadevo.todotrackercompose.ui.screens.homescreen

import com.mostafadevo.todotrackercompose.data.local.Todo

sealed class HomeScreenUiEvent {
  data class ToggleSortingMenu(
    val isMenuExpaneded: Boolean,
  ) : HomeScreenUiEvent()

  data class SortTodos(
    val sortingOption: SortingOptions,
  ) : HomeScreenUiEvent()

  object AddTodoButton : HomeScreenUiEvent()

  data class OnCheckTodo(
    val currentTodo: Todo,
    val isComplete: Boolean,
  ) : HomeScreenUiEvent()

  data class SelectSegment(
    val selctedSegment: Int,
  ) : HomeScreenUiEvent()

  data class OnTodoClick(
    val todo: Todo,
  ) : HomeScreenUiEvent()

  object OnDismissTodoDetailesBottomSheet : HomeScreenUiEvent()

  object OnDeleteAllTodos : HomeScreenUiEvent()
}
