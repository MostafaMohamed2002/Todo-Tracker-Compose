package com.mostafadevo.todotrackercompose.ui.screens.homescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mostafadevo.todotrackercompose.domain.usecase.GetAllTodosUseCase
import com.mostafadevo.todotrackercompose.domain.usecase.UpdateTodoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllTodosUseCase: GetAllTodosUseCase,
    private val updateTodoUseCase: UpdateTodoUseCase

) : ViewModel() {
    private val _uiState: MutableStateFlow<HomeScreenSUitate> =
        MutableStateFlow(HomeScreenSUitate())
    val uiState = _uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeScreenSUitate()
    )


    private val _uiEvent = Channel<HomeScreenUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val debouncePeriod = 50L // 50 milliseconds debounce period

    init {
        loadTodos()
    }

    @OptIn(FlowPreview::class)
    private fun loadTodos() {
        viewModelScope.launch {
            _uiState.debounce(debouncePeriod).collectLatest { state ->
                Timber.d("UI state is $state")
                val todosFlow = when (state.selectedSegmentIndex) {
                    0 -> {
                        when (state.sortingOption) {
                            SortingOptions.BY_TITLE -> {
                                getAllTodosUseCase(
                                    showCompleted = state.selectedSegmentIndex,
                                    sortingOptions = state.sortingOption
                                )
                            }

                            SortingOptions.BY_PRIORITY -> {
                                getAllTodosUseCase(
                                    showCompleted = state.selectedSegmentIndex,
                                    sortingOptions = state.sortingOption
                                )
                            }
                        }
                    }

                    1 -> {
                        when (state.sortingOption) {
                            SortingOptions.BY_TITLE -> {
                                getAllTodosUseCase(
                                    showCompleted = state.selectedSegmentIndex,
                                    sortingOptions = state.sortingOption
                                )
                            }

                            SortingOptions.BY_PRIORITY -> {
                                getAllTodosUseCase(
                                    showCompleted = state.selectedSegmentIndex,
                                    sortingOptions = state.sortingOption
                                )
                            }
                        }
                    }

                    else -> {
                        throw IllegalStateException("Invalid segment index")
                    }
                }
                todosFlow.collectLatest { todos ->
                    Timber.d("Loading todos: ${todos.map { it.title }}")
                    _uiState.value = state.copy(todos = todos)
                }
            }
        }
    }

    fun onEvent(event: HomeScreenUiEvent) {
        when (event) {
            HomeScreenUiEvent.AddTodo -> {
                viewModelScope.launch {
                    _uiEvent.send(HomeScreenUiEvent.AddTodo)
                }
            }

            is HomeScreenUiEvent.SortTodos -> {
                when (event.sortingOption) {
                    SortingOptions.BY_TITLE -> {
                        _uiState.value = _uiState.value.copy(
                            sortingOption = SortingOptions.BY_TITLE
                        )
                    }

                    SortingOptions.BY_PRIORITY -> {
                        _uiState.value = _uiState.value.copy(
                            sortingOption = SortingOptions.BY_PRIORITY
                        )
                    }
                }
            }

            is HomeScreenUiEvent.ToggleSortingMenu -> {
                _uiState.value = _uiState.value.copy(
                    isMenuExpanded = event.isMenuExpaneded
                )
            }

            is HomeScreenUiEvent.onCheckTodo -> {
                viewModelScope.launch(Dispatchers.IO) {
                    viewModelScope.launch {
                        updateTodoUseCase(event.currentTodo.copy(isCompleted = event.isComplete))
                    }
                }
            }

            is HomeScreenUiEvent.SelectSegment -> {
                when (event.selctedSegment) {
                    0 /*0 mean selected to-do*/ -> {
                        _uiState.value = _uiState.value.copy(
                            selectedSegmentIndex = 0
                        )
                    }

                    1/*1 mens selected done*/ -> {
                        _uiState.value = _uiState.value.copy(
                            selectedSegmentIndex = 1
                        )
                    }

                }
            }
        }
    }
}
