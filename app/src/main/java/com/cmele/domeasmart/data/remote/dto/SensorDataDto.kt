package com.cmele.domeasmart.data.remote.dto

data class SensorValueDto(
    val tipo: String? = null,
    val valore: String? = null,
    val stanza: String? = null,
    val timestamp: String? = null
)

data class SensorDataRequestDto(
    val valori: List<SensorValueDto>
)