package com.cmele.domeasmart.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepo @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    companion object {
        private val SERVER_URL_KEY = stringPreferencesKey("server_url")
        private const val DEFAULT_URL = "https://urlserver/"
    }

    val serverUrl: Flow<String> = dataStore.data.map { preferences ->
        preferences[SERVER_URL_KEY] ?: DEFAULT_URL
    }

    suspend fun saveServerUrl(url: String) {
        dataStore.edit { preferences ->
            preferences[SERVER_URL_KEY] = url
        }
    }
}