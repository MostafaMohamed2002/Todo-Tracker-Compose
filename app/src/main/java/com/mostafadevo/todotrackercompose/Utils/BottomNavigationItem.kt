package com.mostafadevo.todotrackercompose.Utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavigationItem(val route: String, val icon: ImageVector, val title: String) {
    object Home : BottomNavigationItem("home", Icons.Default.Home, "Home")
    object Setting : BottomNavigationItem("setting", Icons.Default.Settings, "Settings")
    object Search : BottomNavigationItem("search", Icons.Default.Search, "Search")
}