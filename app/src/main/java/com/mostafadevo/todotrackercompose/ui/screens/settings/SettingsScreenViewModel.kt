package com.mostafadevo.todotrackercompose.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mostafadevo.todotrackercompose.domain.repository.IDataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsScreenViewModel
@Inject
constructor(
  private val datastore: IDataStoreRepository,
) : ViewModel() {
  private val _uiState = MutableStateFlow(SettingsScreenUiState())
  val uiState = _uiState.asStateFlow()

  init {
    viewModelScope.launch {
      combine(
        datastore.isDarkTheme(),
        datastore.isDynamicColors(),
      ) { isDarkTheme, isDynamicColors ->
        SettingsScreenUiState(isDarkTheme = isDarkTheme, isDynamicColors = isDynamicColors)
      }.collect { newState ->
        _uiState.value = newState
      }
    }
  }

  fun onEvent(event: SettingsScreenUiEvents) {
    when (event) {
      is SettingsScreenUiEvents.ToggleDarkTheme -> {
        viewModelScope.launch {
          datastore.toggleDarkTheme(event.isDarkTheme)
        }
      }

      is SettingsScreenUiEvents.ToggleDynamicColors -> {
        viewModelScope.launch {
          datastore.toggleDynamicColors(event.isDynamicColors)
        }
      }
    }
  }
}
