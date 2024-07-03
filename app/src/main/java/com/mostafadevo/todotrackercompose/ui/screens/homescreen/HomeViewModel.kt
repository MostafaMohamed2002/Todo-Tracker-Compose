package com.mostafadevo.todotrackercompose.ui.screens.homescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mostafadevo.todotrackercompose.domain.usecase.AddTodoUseCase
import com.mostafadevo.todotrackercompose.domain.usecase.GetAllTodosSortedByPriorityUseCase
import com.mostafadevo.todotrackercompose.domain.usecase.GetAllTodosSortedByTitleUseCase
import com.mostafadevo.todotrackercompose.domain.usecase.GetAllTodosUseCase
import com.mostafadevo.todotrackercompose.domain.usecase.UpdateTodoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllTodosUseCase: GetAllTodosUseCase,
    private val getAllTodosSortedByTitleUseCase: GetAllTodosSortedByTitleUseCase,
    private val getAllTodosSortedByPriorityUseCase: GetAllTodosSortedByPriorityUseCase,
    private val addTodoUseCase: AddTodoUseCase, private val updateTodoUseCase: UpdateTodoUseCase

) : ViewModel() {
    private val _uiState: MutableStateFlow<HomeScreenSUitate> =
        MutableStateFlow(HomeScreenSUitate())
    val uiState = _uiState.asStateFlow()

    init {
        loadTodos()
    }

    private fun loadTodos() {
        viewModelScope.launch {
            getAllTodosUseCase().collect { todos ->
                _uiState.value = _uiState.value.copy(
                    todos = todos
                )
            }
        }
    }

    fun onEvent(event: HomeScreenUiEvent) {
        when (event) {
            HomeScreenUiEvent.AddTodo -> TODO()
            is HomeScreenUiEvent.SortTodos -> {
                when (event.sortingOption) {
                    SortingOptions.BY_TITLE -> {
                        viewModelScope.launch {
                            getAllTodosSortedByTitleUseCase().collect {
                                _uiState.value = _uiState.value.copy(
                                    todos = it,
                                    isMenuExpanded = false
                                )
                            }
                        }
                    }

                    SortingOptions.BY_PRIORITY -> {
                        viewModelScope.launch {
                            getAllTodosSortedByPriorityUseCase().collect {
                                _uiState.value = _uiState.value.copy(
                                    todos = it,
                                    isMenuExpanded = false
                                )
                            }
                        }
                    }
                }
            }

            is HomeScreenUiEvent.ToggleSortingMenu -> {
                _uiState.value = _uiState.value.copy(
                    isMenuExpanded = event.isMenuExpaneded
                )
            }

            is HomeScreenUiEvent.onCheckTodo -> {
                viewModelScope.launch {
                    updateTodoUseCase(event.todo.copy(isCompleted = event.isComplete))
                }
            }
        }
    }
}