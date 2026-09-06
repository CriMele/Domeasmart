package com.cmele.domeasmart.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmele.domeasmart.data.remote.dto.StanzaDto
import com.cmele.domeasmart.data.repository.DomeasmartRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StanzeViewModel @Inject constructor(
    private val repository: DomeasmartRepo
) : ViewModel() {

    private val _stanze = MutableStateFlow<List<StanzaDto>>(emptyList())
    val stanze: StateFlow<List<StanzaDto>> = _stanze

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        loadStanze()
    }

    fun loadStanze() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.getStanze()
                if (response.isSuccessful) {
                    _stanze.value = response.body() ?: emptyList()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addStanza(name: String) {
        viewModelScope.launch {
            try {
                val response = repository.addStanza(name)
                if (response.isSuccessful) {
                    loadStanze()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteStanza(id: Int) {
        // TO DO - delete stanza
    }
}