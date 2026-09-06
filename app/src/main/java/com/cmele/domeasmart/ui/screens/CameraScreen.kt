package com.cmele.domeasmart.ui.screens

import android.content.Intent
import android.net.Uri
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.cmele.domeasmart.ui.viewmodel.CameraViewModel

@Composable
fun CameraScreen(viewModel: CameraViewModel = hiltViewModel()) {
    val screenshotUrl by viewModel.screenshotUrl.collectAsStateWithLifecycle()
    val serverBaseUrl by viewModel.serverBaseUrl.collectAsStateWithLifecycle()
    val context = LocalContext.current

    var streamUrl1 by remember(serverBaseUrl) {
        mutableStateOf("${serverBaseUrl}streaming")
    }
    var streamUrl2 by remember(serverBaseUrl) {
        mutableStateOf("${serverBaseUrl}streaming2")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text("Camera & Streaming", style = MaterialTheme.typography.headlineMedium)

        Text(
            "Server: $serverBaseUrl",
            style = MaterialTheme.typography.bodySmall
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { viewModel.takeScreenshot() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cattura Screenshot")
        }

        screenshotUrl?.let { url ->
            Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                AsyncImage(
                    model = url,
                    contentDescription = "Screenshot",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "Streaming nel browser",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(streamUrl1))
                context.startActivity(intent)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Apri Streaming 1 nel browser")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(streamUrl2))
                context.startActivity(intent)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Apri Streaming 2 nel browser")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    "Nota",
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    "Lo streaming MJPEG richiede un browser esterno. " +
                            "Assicurati di avere un browser installato.",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}