package com.mostafadevo.todotrackercompose.ui.screens.homescreen.edittodoscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mostafadevo.todotrackercompose.Utils.combineDateAndTime
import com.mostafadevo.todotrackercompose.Utils.extractDate
import com.mostafadevo.todotrackercompose.Utils.extractTime
import com.mostafadevo.todotrackercompose.data.local.Todo
import com.mostafadevo.todotrackercompose.domain.usecase.UpdateTodoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedEditViewModel @Inject constructor(
  private val updateTodoUseCase: UpdateTodoUseCase
) : ViewModel() {
  private val _uiState = MutableStateFlow(EditScreenUiState())
  val uiState = _uiState.asStateFlow()

  // setter for to-do
  fun setTodo(todo: Todo) {
    _uiState.value = uiState.value.copy(
      id = todo.id,
      title = todo.title,
      description = todo.description,
      date = todo.dueDateTime.extractDate(),
      time = todo.dueDateTime.extractTime(),
      priority = todo.priority,
      isAlarmEnabled = todo.isAlarmEnabled,
    )
  }

  fun onEvent(event: EditScreenEvents) {
    when (event) {
      is EditScreenEvents.OnAlarmToggle -> {
        _uiState.value = uiState.value.copy(isAlarmEnabled = !uiState.value.isAlarmEnabled)
      }

      is EditScreenEvents.OnDateChange -> {
        _uiState.value = uiState.value.copy(date = event.date)
      }

      is EditScreenEvents.OnDescriptionChange -> {
        _uiState.value = uiState.value.copy(description = event.description)
      }

      is EditScreenEvents.OnPriorityChange -> {
        _uiState.value = uiState.value.copy(priority = event.priority)

      }

      EditScreenEvents.OnSaveClick -> {
        val todo = Todo(
          id = _uiState.value.id,
          title = _uiState.value.title,
          description = _uiState.value.description,
          priority = _uiState.value.priority,
          isCompleted = false,
          isAlarmEnabled = _uiState.value.isAlarmEnabled,
          dueDateTime = combineDateAndTime(
            _uiState.value.date,
            _uiState.value.time
          ),
        )
        viewModelScope.launch {
          updateTodoUseCase(todo)
        }
      }

      is EditScreenEvents.OnTimeChange -> {
        _uiState.value = uiState.value.copy(time = event.time.extractTime())
      }

      is EditScreenEvents.OnTitleChange -> {
        _uiState.value = uiState.value.copy(title = event.title)
      }
    }
  }
}
