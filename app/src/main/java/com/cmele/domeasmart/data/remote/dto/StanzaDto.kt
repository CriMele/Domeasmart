package com.cmele.domeasmart.data.remote.dto

data class StanzaDto(
    val id: Int,
    val name: String
)

data class StanzaCreateDto(
    val name: String
)