package com.cmele.domeasmart.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ServerStatsDto(
    val ok: String,
    @SerializedName("statsServer")
    val statsServer: Set<com.google.gson.JsonElement>
)