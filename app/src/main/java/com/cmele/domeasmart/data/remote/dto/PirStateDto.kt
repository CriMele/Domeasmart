package com.cmele.domeasmart.data.remote.dto

import com.google.gson.annotations.SerializedName

data class PirStateDto(
    @SerializedName("statusPIR")
    val statusPIR: Any? = null,

    @SerializedName("pirState")
    val pirState: Any? = null
) {
    fun getStatusPIRBoolean(): Boolean? {
        return when (statusPIR) {
            is Boolean -> statusPIR
            is List<*> -> statusPIR.firstOrNull() as? Boolean
            else -> null
        }
    }
}