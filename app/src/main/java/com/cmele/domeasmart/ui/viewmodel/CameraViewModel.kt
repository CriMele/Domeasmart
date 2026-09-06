package com.cmele.domeasmart.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmele.domeasmart.data.repository.DomeasmartRepo
import com.cmele.domeasmart.data.repository.SettingsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val repository: DomeasmartRepo,
    private val settingsRepository: SettingsRepo
) : ViewModel() {

    private val _serverBaseUrl = MutableStateFlow("https://serverurl/")
    val serverBaseUrl: StateFlow<String> = _serverBaseUrl.asStateFlow()

    private val _screenshotUrl = MutableStateFlow<String?>(null)
    val screenshotUrl: StateFlow<String?> = _screenshotUrl.asStateFlow()

    init {
        loadServerUrl()
    }

    private fun loadServerUrl() {
        viewModelScope.launch {
            val url = settingsRepository.serverUrl.first()
            _serverBaseUrl.value = url
        }
    }

    fun takeScreenshot() {
        viewModelScope.launch {
            try {
                val response = repository.getScreenshot()
                if (response.isSuccessful) {
                    val baseUrl = _serverBaseUrl.value
                    _screenshotUrl.value = "${baseUrl}screenshot?t=${System.currentTimeMillis()}"
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}