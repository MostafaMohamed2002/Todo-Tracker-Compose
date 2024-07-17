package com.mostafadevo.todotrackercompose.data.local.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.mostafadevo.todotrackercompose.Utils.Constants
import com.mostafadevo.todotrackercompose.Utils.Constants.DARK_THEME_KEY
import com.mostafadevo.todotrackercompose.Utils.Constants.DYNAMIC_COLORS_KEY
import com.mostafadevo.todotrackercompose.domain.repository.IDataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by
preferencesDataStore(name = Constants.DATASTORE_PREFERENCES_NAME)

class DataStoreRepositoryImpl @Inject constructor(
    private val context: Context
) : IDataStoreRepository {
    override fun isDarkTheme(): Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[DARK_THEME_KEY] as? Boolean ?: false // Safe cast with default
        }

    override fun isDynamicColors(): Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[DYNAMIC_COLORS_KEY] as? Boolean ?: false // Safe cast with default
        }

    override suspend fun toggleDarkTheme(isDarkTheme: Boolean) {
        try {
            context.dataStore.edit { settings ->
                settings[DARK_THEME_KEY] = isDarkTheme
                Timber.d("toggleDarkTheme: $isDarkTheme")
            }
        } catch (e: Exception) {
            Timber.e(e, "Failed to toggle dark theme.")
        }
    }

    override suspend fun toggleDynamicColors(isDynamicColors: Boolean) {
        try {
            context.dataStore.edit { settings ->
                settings[DYNAMIC_COLORS_KEY] = isDynamicColors
                Timber.d("toggleDynamicColors: $isDynamicColors")
            }
        } catch (e: Exception) {
            Timber.e(e, "Failed to toggle dynamic colors.")
        }
    }
}