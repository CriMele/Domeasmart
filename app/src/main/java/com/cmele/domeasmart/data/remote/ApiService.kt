package com.cmele.domeasmart.data.remote

import com.cmele.domeasmart.data.remote.dto.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("statsServer")
    suspend fun getServerStats(): Response<ServerStatsDto>

    @GET("aggiornadatisens")
    suspend fun updateSensorData(
        @Query("stanzaSel") stanzaSel: Int
    ): Response<SensorUpdateDto>

    @POST("data")
    suspend fun sendSensorData(
        @Body data: SensorDataRequestDto
    ): Response<GenericResponseDto>

    @GET("ping")
    suspend fun ping(): Response<GenericResponseDto>

    @GET("pir_on")
    suspend fun setPirState(
        @Query("attivoPir") attivoPir: Boolean
    ): Response<PirStateDto>

    @GET("stato_porta")
    suspend fun getDoorState(): Response<DoorStateDto>

    @GET("chiudi")
    suspend fun cleanupGpio(): Response<GenericResponseDto>

    @GET("pir_state")
    suspend fun getPirState(): Response<PirStateDto>

    @GET("screenshot")
    suspend fun getScreenshot(): Response<okhttp3.ResponseBody>

    @GET("stanze")
    suspend fun getStanze(): Response<List<StanzaDto>>

    @GET("stanze/{stanzaId}/{tipologia}/now")
    suspend fun getLatestValue(
        @Path("stanzaId") stanzaId: String,
        @Path("tipologia") tipologia: String
    ): Response<ValoreSensoreDto>

    @GET("stanze/{stanzaId}/{tipologia}/last={numRec}")
    suspend fun getLastValues(
        @Path("stanzaId") stanzaId: Int,
        @Path("tipologia") tipologia: String,
        @Path("numRec") numRec: Int
    ): Response<List<ValoreSensoreDto>>

    @POST("addstanza/")
    suspend fun addStanza(
        @Body stanza: StanzaCreateDto
    ): Response<StanzaDto>
}