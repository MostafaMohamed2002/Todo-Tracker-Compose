package com.mostafadevo.todotrackercompose.ui.screens.settings

sealed class SettingsScreenUiEvents {
  data class ToggleDarkTheme(
    val isDarkTheme: Boolean,
  ) : SettingsScreenUiEvents()

  data class ToggleDynamicColors(
    val isDynamicColors: Boolean,
  ) : SettingsScreenUiEvents()
}
