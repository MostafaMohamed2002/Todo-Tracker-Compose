package com.mostafadevo.todotrackercompose.ui.screens.searchscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mostafadevo.todotrackercompose.domain.usecase.QueryTodosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val queryTodosUseCase: QueryTodosUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<SearchScreenUiState>(SearchScreenUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: SearchScreenUiEvents) {
        when (event) {
            is SearchScreenUiEvents.onQueryChange -> {
                _uiState.value = _uiState.value.copy(
                    query = event.query
                )
            }

            SearchScreenUiEvents.onSearch -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val todos =
                        queryTodosUseCase(_uiState.value.query).first()
                    withContext(Dispatchers.Main) {
                        _uiState.value = _uiState.value.copy(
                            todos = todos
                        )
                    }
                }
            }

            SearchScreenUiEvents.onClearSearchText -> {
                _uiState.value = _uiState.value.copy(
                    query = ""
                )
            }
        }
    }
}