package com.cmele.domeasmart.utils

object DataUtils {

    fun formatServerData(orMap: Map<String, Any?>?): Map<String, String> {
        if (orMap == null) return emptyMap()

        val formatedMap = mutableMapOf<String, String>()

        orMap.forEach { (key, value) ->
            val stringValue = value?.toString() ?: "N/D"

            val minusKey = key.lowercase()

            val (newKey, newValue) = when {
                minusKey.contains("stanza") || minusKey.contains("room") -> {
                    "Locazione" to stringValue
                }
                minusKey.contains("cpu") && minusKey.contains("temp") -> {
                    "Temperatura CPU" to "$stringValue°C"
                }
                minusKey.contains("mem") || minusKey.contains("usage") -> {
                    "Utilizzo Memoria" to stringValue
                }

                minusKey.contains("temp") -> {
                    "Temperatura" to "$stringValue°C"
                }
                minusKey.contains("hum") || minusKey.contains("umid") -> {
                    "Umidità" to "$stringValue%"
                }
                minusKey.contains("lux") || minusKey.contains("luce") -> {
                    "Luminosità" to "$stringValue lx"
                }

                else -> key to stringValue
            }

            formatedMap[newKey] = newValue
        }

        return formatedMap
    }
}


