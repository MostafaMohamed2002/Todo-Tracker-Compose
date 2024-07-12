package com.mostafadevo.todotrackercompose.ui.screens.searchscreen

sealed class SearchScreenUiEvents {
    data class onQueryChange(val query: String) : SearchScreenUiEvents()
    object onSearch : SearchScreenUiEvents()
    object onClearSearchText : SearchScreenUiEvents()
}