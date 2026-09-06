package com.cmele.domeasmart.data.repository


import com.cmele.domeasmart.data.remote.ApiService
import com.cmele.domeasmart.data.remote.dto.*
import retrofit2.Response
import javax.inject.Inject

class DomeasmartRepoImpl
    @Inject constructor(
        private val apiService: ApiService
    ) : DomeasmartRepo {

        override suspend fun getServerStats() = apiService.getServerStats()
        override suspend fun updateSensorData(stanzaSel: Int) = apiService.updateSensorData(stanzaSel)
        override suspend fun sendSensorData(data: SensorDataRequestDto) = apiService.sendSensorData(data)
        override suspend fun ping() = apiService.ping()
        override suspend fun setPirState(attivo: Boolean) = apiService.setPirState(attivo)
        override suspend fun getDoorState() = apiService.getDoorState()
        override suspend fun cleanupGpio() = apiService.cleanupGpio()
        override suspend fun getPirState() = apiService.getPirState()
        override suspend fun getScreenshot() = apiService.getScreenshot()
        override suspend fun getStanze() = apiService.getStanze()
        override suspend fun getLatestValue(stanzaId: String, tipologia: String) = apiService.getLatestValue(stanzaId, tipologia)
        override suspend fun getLastValues(stanzaId: Int, tipologia: String, numRec: Int) = apiService.getLastValues(stanzaId, tipologia, numRec)
        override suspend fun addStanza(name: String) = apiService.addStanza(StanzaCreateDto(name))
    }