package com.cmele.domeasmart.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmele.domeasmart.data.repository.DomeasmartRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: DomeasmartRepo
) : ViewModel() {

    private val _serverStatus = MutableStateFlow<String?>(null)
    val serverStatus: StateFlow<String?> = _serverStatus

    private val _datiServer = MutableStateFlow<Map<String, Any?>?>(null)
    val datiServer: StateFlow<Map<String, Any?>?> = _datiServer

    private val _doorState = MutableStateFlow<String?>(null)
    val doorState: StateFlow<String?> = _doorState

    private val _pirState = MutableStateFlow<Boolean?>(null)
    val pirState: StateFlow<Boolean?> = _pirState

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        checkServer()
        loadDoorState()
        loadPirState()
        loadDatiServer(2)
    }

    fun checkServer() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.ping()

                if (response.isSuccessful) {
                    _serverStatus.value = response.body()?.status
                } else {
                    _error.value = "Server offline"
                }
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadDatiServer(stanzaSel: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.updateSensorData(stanzaSel)
                if (response.isSuccessful) {
                    val mappaDati = response.body()?.result as? Map<String, Any?>
                    _datiServer.value = mappaDati

                    Log.d("DatiServer", "Mappa ricevuta da Gson: $mappaDati")
                }
            } catch (e: Exception) {
                Log.d("Error", "Errore: ${e.message}")
                _datiServer.value = null
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadDoorState() {
        viewModelScope.launch {
            try {
                val response = repository.getDoorState()
                if (response.isSuccessful) {
                    _doorState.value = response.body()?.stato
                }
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun togglePir() {
        viewModelScope.launch {
            try {
                val current = _pirState.value ?: false
                val response = repository.setPirState(!current)
                if (response.isSuccessful) {

                    _pirState.value = response.body()?.getStatusPIRBoolean()
                }
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun loadPirState() {
        viewModelScope.launch {
            try {
                val response = repository.getPirState()
                if (response.isSuccessful) {
                    _pirState.value = response.body()?.getStatusPIRBoolean()
                }
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun cleanupGpio() {
        viewModelScope.launch {
            try {
                repository.cleanupGpio()
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}
