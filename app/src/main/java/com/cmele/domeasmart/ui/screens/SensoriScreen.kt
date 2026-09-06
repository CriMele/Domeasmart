package com.cmele.domeasmart.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cmele.domeasmart.ui.viewmodel.SensoriViewModel

@Composable
fun SensoriScreen(viewModel: SensoriViewModel = hiltViewModel()) {
    var stanzaId by remember { mutableStateOf("1") }
    var tipologia by remember { mutableStateOf("temperatura") }
    val result by viewModel.result.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text("Sensori", style = MaterialTheme.typography.headlineMedium)

        OutlinedTextField(
            value = stanzaId,
            onValueChange = { stanzaId = it },
            label = { Text("ID Stanza") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = tipologia,
            onValueChange = { tipologia = it },
            label = { Text("Tipologia") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
            Button(
                onClick = { viewModel.aggiornaDati(stanzaId.toIntOrNull() ?: 1) },
                modifier = Modifier.weight(1f)
            ) { Text("Aggiorna Dati") }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = { viewModel.getLatestValue(stanzaId, tipologia) },
                modifier = Modifier.weight(1f)
            ) { Text("Ultimo Valore") }
        }

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        }

        result?.let {
            Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                Text(
                    text = it.toString(),
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}