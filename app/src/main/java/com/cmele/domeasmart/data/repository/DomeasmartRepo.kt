package com.cmele.domeasmart.data.repository

import com.cmele.domeasmart.data.remote.dto.*
import retrofit2.Response

interface DomeasmartRepo {
    suspend fun getServerStats(): Response<ServerStatsDto>
    suspend fun updateSensorData(stanzaSel: Int): Response<SensorUpdateDto>
    suspend fun sendSensorData(data: SensorDataRequestDto): Response<GenericResponseDto>
    suspend fun ping(): Response<GenericResponseDto>
    suspend fun setPirState(attivo: Boolean): Response<PirStateDto>
    suspend fun getDoorState(): Response<DoorStateDto>
    suspend fun cleanupGpio(): Response<GenericResponseDto>
    suspend fun getPirState(): Response<PirStateDto>
    suspend fun getScreenshot(): Response<okhttp3.ResponseBody>
    suspend fun getStanze(): Response<List<StanzaDto>>
    suspend fun getLatestValue(stanzaId: String, tipologia: String): Response<ValoreSensoreDto>
    suspend fun getLastValues(stanzaId: Int, tipologia: String, numRec: Int): Response<List<ValoreSensoreDto>>
    suspend fun addStanza(name: String): Response<StanzaDto>
}