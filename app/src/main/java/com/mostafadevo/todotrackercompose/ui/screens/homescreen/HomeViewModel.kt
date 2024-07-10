package com.mostafadevo.todotrackercompose.ui.screens.homescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mostafadevo.todotrackercompose.Utils.combineDateAndTime
import com.mostafadevo.todotrackercompose.Utils.extractTime
import com.mostafadevo.todotrackercompose.data.local.Todo
import com.mostafadevo.todotrackercompose.domain.usecase.AddTodoUseCase
import com.mostafadevo.todotrackercompose.domain.usecase.DeleteTodoUseCase
import com.mostafadevo.todotrackercompose.domain.usecase.GetAllTodosUseCase
import com.mostafadevo.todotrackercompose.domain.usecase.GetAllTodosUseCaseWithOptions
import com.mostafadevo.todotrackercompose.domain.usecase.UpdateTodoUseCase
import com.mostafadevo.todotrackercompose.ui.screens.homescreen.AddTodo.AddTodoDialogUiEvents
import com.mostafadevo.todotrackercompose.ui.screens.homescreen.AddTodo.AddTodoDialogUiState
import com.mostafadevo.todotrackercompose.ui.screens.homescreen.BottomSheetUiEventsFromViewModel.close
import com.mostafadevo.todotrackercompose.ui.screens.homescreen.todoDetailes.TodoDetailesBottomSheetUiEvent
import com.mostafadevo.todotrackercompose.ui.screens.homescreen.todoDetailes.TodoDetailesBottomSheetUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllTodosWithOptionsUseCase: GetAllTodosUseCaseWithOptions,
    private val getAllTodosUseCase: GetAllTodosUseCase,
    private val updateTodoUseCase: UpdateTodoUseCase,
    private val addTodoUseCase: AddTodoUseCase,
    private val deleteTodoUseCase: DeleteTodoUseCase

) : ViewModel() {
    //home screen state
    private val _homeUiState: MutableStateFlow<HomeScreenSUitate> =
        MutableStateFlow(HomeScreenSUitate())
    val homeUiState = _homeUiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeScreenSUitate()
    )

    //dialog state
    private val _addTodoDialogUiState: MutableStateFlow<AddTodoDialogUiState> =
        MutableStateFlow(AddTodoDialogUiState())
    val addTodoDialogUiState = _addTodoDialogUiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = AddTodoDialogUiState()
    )

    //bottom sheet state
    private val _todoDetailesBottomSheetUiState: MutableStateFlow<TodoDetailesBottomSheetUiState> =
        MutableStateFlow(
            TodoDetailesBottomSheetUiState()
        )
    val todoDetailesBottomSheetUiState = _todoDetailesBottomSheetUiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = TodoDetailesBottomSheetUiState()
    )

    private val _BottomSheetUiEventsFromViewModel = Channel<BottomSheetUiEventsFromViewModel>()
    val BottomSheetUiEventsFromViewModel = _BottomSheetUiEventsFromViewModel.receiveAsFlow()

    private val debouncePeriod = 50L // 50 milliseconds debounce period

    init {
        loadTodos()
    }

    @OptIn(FlowPreview::class)
    private fun loadTodos() {
        viewModelScope.launch {
            _homeUiState.debounce(debouncePeriod).collectLatest { state ->
                updateBottomSheetState() // fixes the issue when click to-do and the bottom sheet is not updated
                Timber.d("UI state is $state")
                val todosFlow = when (state.selectedSegmentIndex) {
                    0 -> {
                        when (state.sortingOption) {
                            SortingOptions.BY_TITLE -> {
                                getAllTodosWithOptionsUseCase(
                                    showCompleted = state.selectedSegmentIndex,
                                    sortingOptions = state.sortingOption
                                )
                            }

                            SortingOptions.BY_PRIORITY -> {
                                getAllTodosWithOptionsUseCase(
                                    showCompleted = state.selectedSegmentIndex,
                                    sortingOptions = state.sortingOption
                                )
                            }
                        }
                    }

                    1 -> {
                        when (state.sortingOption) {
                            SortingOptions.BY_TITLE -> {
                                getAllTodosWithOptionsUseCase(
                                    showCompleted = state.selectedSegmentIndex,
                                    sortingOptions = state.sortingOption
                                )
                            }

                            SortingOptions.BY_PRIORITY -> {
                                getAllTodosWithOptionsUseCase(
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
                    _homeUiState.value = state.copy(todos = todos)
                }
            }
        }
    }

    private fun updateBottomSheetState() {
        val currentTodo = homeUiState.value.currentShownTodo
        if (currentTodo != null) {
            _todoDetailesBottomSheetUiState.value = TodoDetailesBottomSheetUiState(
                id = currentTodo.id,
                title = currentTodo.title,
                description = currentTodo.description!!,
                priority = currentTodo.priority,
                date = currentTodo.dueDateTime,
                time = currentTodo.dueDateTime.extractTime(),
                isEditMode = false,
                isAlarmEnabled = currentTodo.isAlarmEnabled,
                isCompleted = currentTodo.isCompleted
            )
        }
    }

    fun onEvent(event: HomeScreenUiEvent) {
        when (event) {
            HomeScreenUiEvent.AddTodoButton -> {
                _homeUiState.value = _homeUiState.value.copy(
                    isAddTodoDialogOpen = true
                )
            }

            is HomeScreenUiEvent.SortTodos -> {
                when (event.sortingOption) {
                    SortingOptions.BY_TITLE -> {
                        _homeUiState.value = _homeUiState.value.copy(
                            sortingOption = SortingOptions.BY_TITLE
                        )
                    }

                    SortingOptions.BY_PRIORITY -> {
                        _homeUiState.value = _homeUiState.value.copy(
                            sortingOption = SortingOptions.BY_PRIORITY
                        )
                    }
                }
            }

            is HomeScreenUiEvent.ToggleSortingMenu -> {
                _homeUiState.value = _homeUiState.value.copy(
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
                        _homeUiState.value = _homeUiState.value.copy(
                            selectedSegmentIndex = 0
                        )
                    }

                    1/*1 mens selected done*/ -> {
                        _homeUiState.value = _homeUiState.value.copy(
                            selectedSegmentIndex = 1
                        )
                    }

                }
            }

            is HomeScreenUiEvent.onTodoClick -> {
                _homeUiState.value = _homeUiState.value.copy(
                    isTodoDetailesBottomSheetEnabled = true, currentShownTodo = event.todo
                )
                Timber.d("current shown todo is ${event.todo.title}")
            }

            HomeScreenUiEvent.onDismissTodoDetailesBottomSheet -> {
                _homeUiState.value = _homeUiState.value.copy(
                    isTodoDetailesBottomSheetEnabled = false, currentShownTodo = null
                )
            }

            HomeScreenUiEvent.onDeleteAllTodos -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val alltodos = getAllTodosUseCase.invoke().first()
                    alltodos.forEach { deleteTodoUseCase(it) }
                }
            }
        }
    }

    fun onDialogEvent(events: AddTodoDialogUiEvents) {
        when (events) {
            AddTodoDialogUiEvents.AddTodo -> {
                viewModelScope.launch(Dispatchers.IO) {
                    addTodoUseCase(
                        Todo(
                            id = UUID.randomUUID().hashCode(),
                            title = _addTodoDialogUiState.value.title,
                            description = _addTodoDialogUiState.value.description,
                            priority = _addTodoDialogUiState.value.priority,
                            isCompleted = false,
                            dueDateTime = combineDateAndTime(
                                _addTodoDialogUiState.value.date, _addTodoDialogUiState.value.time
                            ),
                            isAlarmEnabled = _addTodoDialogUiState.value.isAlarmEnabled
                        )
                    )
                    //reset dialog state After adding to-do
                    withContext(Dispatchers.Main) {
                        _addTodoDialogUiState.value = AddTodoDialogUiState()
                    }
                }


            }

            AddTodoDialogUiEvents.Close -> {
                _homeUiState.value = _homeUiState.value.copy(
                    isAddTodoDialogOpen = false
                )
                _addTodoDialogUiState.value = AddTodoDialogUiState()

            }

            is AddTodoDialogUiEvents.OnDateChange -> {
                _addTodoDialogUiState.value = _addTodoDialogUiState.value.copy(
                    date = events.date
                )
                Timber.d("selected date : ${_addTodoDialogUiState.value.date}")
            }

            is AddTodoDialogUiEvents.OnDescriptionChange -> {
                _addTodoDialogUiState.value = _addTodoDialogUiState.value.copy(
                    description = events.description
                )
            }

            is AddTodoDialogUiEvents.OnPriorityChange -> {
                _addTodoDialogUiState.value = _addTodoDialogUiState.value.copy(
                    priority = events.priority,
                )
                Timber.d("${events.priority.name.toString()}")
            }

            is AddTodoDialogUiEvents.OnTimeChange -> {
                _addTodoDialogUiState.value = _addTodoDialogUiState.value.copy(
                    time = events.time
                )
                Timber.d("current time is ${events.time.toString()}")
            }

            is AddTodoDialogUiEvents.OnTitleChange -> {
                _addTodoDialogUiState.value = _addTodoDialogUiState.value.copy(
                    title = events.title
                )
            }

            is AddTodoDialogUiEvents.OnSetAlarmChange -> {
                _addTodoDialogUiState.value = _addTodoDialogUiState.value.copy(
                    isAlarmEnabled = events.isEnabled
                )
            }
        }
    }

    fun onBottomSheetEvent(event: TodoDetailesBottomSheetUiEvent) {
        when (event) {

            TodoDetailesBottomSheetUiEvent.ToggleEditMode -> {
                // when the user clicks on the edit button, the bottom sheet will be in edit mode if it was not in edit mode save the current task to the database
                val currentTodo = homeUiState.value.currentShownTodo
                if (currentTodo != null && currentTodo.isCompleted.not()/*don't enable edit mode in done todosz*/) {
                    _todoDetailesBottomSheetUiState.value =
                        _todoDetailesBottomSheetUiState.value.copy(
                            isEditMode = !_todoDetailesBottomSheetUiState.value.isEditMode
                        )
                    if (!_todoDetailesBottomSheetUiState.value.isEditMode) {
                        viewModelScope.launch(Dispatchers.IO) {
                            updateTodoUseCase(
                                currentTodo.copy(
                                    title = _todoDetailesBottomSheetUiState.value.title,
                                    description = _todoDetailesBottomSheetUiState.value.description,
                                    priority = _todoDetailesBottomSheetUiState.value.priority,
                                    dueDateTime = combineDateAndTime(
                                        _todoDetailesBottomSheetUiState.value.date,
                                        _todoDetailesBottomSheetUiState.value.time
                                    ),
                                    isAlarmEnabled = _todoDetailesBottomSheetUiState.value.isAlarmEnabled
                                )
                            )
                            _BottomSheetUiEventsFromViewModel.send(close)
                        }
                    }
                }

            }

            is TodoDetailesBottomSheetUiEvent.onDateChange -> {
                _todoDetailesBottomSheetUiState.value =
                    _todoDetailesBottomSheetUiState.value.copy(date = event.date)
            }

            TodoDetailesBottomSheetUiEvent.onDeleted -> {
                val currentTodo = homeUiState.value.currentShownTodo
                viewModelScope.launch(Dispatchers.IO) {
                    if (currentTodo != null) {
                        deleteTodoUseCase(currentTodo)
                    }
                }
            }

            is TodoDetailesBottomSheetUiEvent.onDescriptionChange -> {
                _todoDetailesBottomSheetUiState.value =
                    _todoDetailesBottomSheetUiState.value.copy(description = event.description)
            }

            is TodoDetailesBottomSheetUiEvent.onPriorityChange -> {
                _todoDetailesBottomSheetUiState.value =
                    _todoDetailesBottomSheetUiState.value.copy(priority = event.priority)
            }

            is TodoDetailesBottomSheetUiEvent.onTimeChange -> {
                Timber.d("time changed to \"${event.time}\"")
                _todoDetailesBottomSheetUiState.value =
                    _todoDetailesBottomSheetUiState.value.copy(time = event.time)
            }

            is TodoDetailesBottomSheetUiEvent.onTitleChange -> {
                Timber.d("title changed to \"${event.title}\"")
                _todoDetailesBottomSheetUiState.value =
                    _todoDetailesBottomSheetUiState.value.copy(title = event.title)
            }
        }
    }
}

sealed class BottomSheetUiEventsFromViewModel {
    object close : BottomSheetUiEventsFromViewModel()
}
