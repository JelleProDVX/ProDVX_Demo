package com.laerodev.androidtest.nfc

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun NfcReaderButton(
    currentNfcId: String,
    onLaunchNfcScan: (Context) -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Main App Screen", style = MaterialTheme.typography.headlineMedium)

        Button(
            onClick = { onLaunchNfcScan(context) },
            modifier = Modifier.padding(top = 24.dp)
        ) {
            Text("Scan NFC Tag")
        }

        TextField(
            value = currentNfcId,
            onValueChange = { /* NFC ID is read-only */ },
            label = { Text("Last Scanned NFC ID") },
            readOnly = true,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}
