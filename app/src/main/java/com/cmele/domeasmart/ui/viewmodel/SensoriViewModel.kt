package com.cmele.domeasmart.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmele.domeasmart.data.repository.DomeasmartRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SensoriViewModel @Inject constructor(
    private val repository: DomeasmartRepo
) : ViewModel() {

    private val _result = MutableStateFlow<Any?>(null)
    val result: StateFlow<Any?> = _result

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun aggiornaDati(stanzaSel: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.updateSensorData(stanzaSel)
                if (response.isSuccessful) {
                    _result.value = response.body()
                }
            } catch (e: Exception) {
                _result.value = "Errore: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getLatestValue(stanzaId: String, tipologia: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.getLatestValue(stanzaId, tipologia)
                if (response.isSuccessful) {
                    _result.value = response.body()
                }
            } catch (e: Exception) {
                _result.value = "Errore: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}