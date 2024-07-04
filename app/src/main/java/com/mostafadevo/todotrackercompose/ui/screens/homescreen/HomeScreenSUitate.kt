package com.mostafadevo.todotrackercompose.ui.screens.homescreen

import com.mostafadevo.todotrackercompose.data.local.Todo

data class HomeScreenSUitate(
    val todos: List<Todo> = emptyList(),
    val sortingOption: SortingOptions = SortingOptions.BY_TITLE,
    val isMenuExpanded: Boolean = false,
    val selectedSegmentIndex: Int = 0 // 0 mean SegmentOptions.to-do
)

enum class SortingOptions {
    BY_TITLE,
    BY_PRIORITY
}

enum class SegmentOptions {
    TODO,
    DONE
}