package com.cmele.domeasmart.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cmele.domeasmart.data.remote.dto.StanzaDto
import com.cmele.domeasmart.ui.viewmodel.StanzeViewModel

@Composable
fun StanzeScreen(viewModel: StanzeViewModel = hiltViewModel()) {
    val stanze by viewModel.stanze.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    var showDialog by remember { mutableStateOf(false) }
    var newStanzaName by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text("Stanze", style = MaterialTheme.typography.headlineMedium)

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        }


        stanze.forEach { stanza ->
            StanzaItem(stanza, onDelete = { viewModel.deleteStanza(stanza.id) })
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { showDialog = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Aggiungi Stanza")
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Nuova Stanza") },
            text = {
                OutlinedTextField(
                    value = newStanzaName,
                    onValueChange = { newStanzaName = it },
                    label = { Text("Nome Stanza") }
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.addStanza(newStanzaName)
                    showDialog = false
                    newStanzaName = ""
                }) { Text("Aggiungi") }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) { Text("Annulla") }
            }
        )
    }
}

@Composable
fun StanzaItem(stanza: StanzaDto, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(stanza.name, style = MaterialTheme.typography.titleMedium)
                Text("ID: ${stanza.id}", style = MaterialTheme.typography.bodySmall)
            }
            TextButton(onClick = onDelete) {
                Text("Elimina", color = MaterialTheme.colorScheme.error)
            }
        }
    }
}