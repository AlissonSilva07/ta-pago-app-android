package br.alisson.edu.tapago.data.utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserManager @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    private object PreferenceKeys {
        val USER_DATA = stringPreferencesKey("user_data")
    }

    val userData: Flow<String?> = dataStore.data.map { preferences ->
        preferences[PreferenceKeys.USER_DATA]
    }

    suspend fun updateUserData(userData: String) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.USER_DATA] = userData
        }
    }

    suspend fun deleteUserData() {
        dataStore.edit { preferences ->
            preferences.remove(PreferenceKeys.USER_DATA)
        }
    }
}