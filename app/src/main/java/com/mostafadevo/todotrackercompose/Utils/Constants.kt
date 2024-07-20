package com.mostafadevo.todotrackercompose.Utils

import androidx.datastore.preferences.core.booleanPreferencesKey

object Constants {
  const val DATASTORE_PREFERENCES_NAME = "settings"
  val DARK_THEME_KEY = booleanPreferencesKey("dark_theme")
  val DYNAMIC_COLORS_KEY = booleanPreferencesKey("dynamic_colors")
}
