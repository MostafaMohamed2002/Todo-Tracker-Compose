package com.mostafadevo.todotrackercompose.domain.repository

import kotlinx.coroutines.flow.Flow

interface IDataStoreRepository {
  fun isDarkTheme(): Flow<Boolean>

  fun isDynamicColors(): Flow<Boolean>

  suspend fun toggleDarkTheme(isDarkTheme: Boolean)

  suspend fun toggleDynamicColors(isDynamicColors: Boolean)
}
