package com.cmele.domeasmart.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.cmele.domeasmart.ui.navigation.Screen
import com.cmele.domeasmart.ui.viewmodel.HomeViewModel
import com.cmele.domeasmart.utils.DataUtils
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.*
import androidx.compose.ui.graphics.Color

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val doorState by viewModel.doorState.collectAsStateWithLifecycle()
    val datiServerRaw by viewModel.datiServer.collectAsStateWithLifecycle()
    val pirState by viewModel.pirState.collectAsStateWithLifecycle()

    val formatDatiServer = remember(datiServerRaw) {
        DataUtils.formatServerData(datiServerRaw)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Domeasmart", style = MaterialTheme.typography.headlineMedium)

        // Dati Server
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                if (formatDatiServer.isNotEmpty()) {
                    Text(
                        text = "Informazioni Sistema",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    formatDatiServer.forEach { (key, value) ->
                        if (key == "Locazione" || key == "Temperatura CPU" || key == "Utilizzo Memoria") {
                            Text(
                                text = "$key: $value",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }
                    }
                } else {
                    Text("Dati Server: Sconosciuto", style = MaterialTheme.typography.titleMedium)
                }
            }
        }

        // Door State
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Stato Porta: ${doorState ?: "Sconosciuto"}", style = MaterialTheme.typography.titleMedium)
            }
        }

        // PIR State
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("PIR State: ${if (pirState == true) "Attivo" else "Inattivo"}", style = MaterialTheme.typography.titleMedium)
            }
        }

        // Ambiente
        Card(modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)            )
        ){
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Condizioni Ambiente",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                if (formatDatiServer.isNotEmpty()) {

                    val tempRaw = (datiServerRaw?.get("temp") as? Number)?.toDouble() ?: 0.0
                    val luxRaw = (datiServerRaw?.get("lux") as? Number)?.toDouble() ?: 0.0

                    val tempColor = if (tempRaw > 30.0) Color.Red else Color(0xFF4AC950)

                    formatDatiServer.forEach { (key, value) ->
                        val keyLowCase = key.lowercase()

                        val (icona, textColor) = when {
                            keyLowCase.contains("temperatura") && !keyLowCase.contains("cpu") -> {
                                Icons.Default.Thermostat to tempColor
                            }
                            keyLowCase.contains("umidità") || keyLowCase.contains("hum") -> {
                                Icons.Default.WaterDrop to MaterialTheme.colorScheme.onSurface
                            }
                            keyLowCase.contains("luminosità") || keyLowCase.contains("lux") -> {
                                val iconaLuce = if (luxRaw < 40.0) Icons.Filled.DarkMode else Icons.Filled.WbSunny
                                iconaLuce to MaterialTheme.colorScheme.onSurface
                            }
                            else -> null to null
                        }

                        if (icona != null && textColor != null) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = icona,
                                    contentDescription = key,
                                    tint = if (icona == Icons.Default.Thermostat) textColor else MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = "$key: ",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = value,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = textColor
                                )
                            }
                        }
                    }
                } else {
                    Text(
                        text = "In attesa di dati dal server...",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(5.dp))

        Button(
            onClick = { viewModel.loadDatiServer(2) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Aggiorna Dati")
        }

        Button(
            onClick = { viewModel.togglePir() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Sensore PIR")
        }
        /*
        Button(
            onClick = { viewModel.cleanupGpio() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Pulisci GPIO")
        }
        */
        Button(
            onClick = { navController.navigate(Screen.Stanze.route) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Gestione Stanze")
        }

        Button(
            onClick = { navController.navigate(Screen.Camera.route) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Camera & Streaming")
        }

        Button(
            onClick = { navController.navigate(Screen.Settings.route) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Impostazioni")
        }
    }
}