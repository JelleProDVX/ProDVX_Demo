package com.laerodev.androidtest

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.laerodev.androidtest.inactivity.InactivityScreen
import com.laerodev.androidtest.nfc.NfcActivity
import com.laerodev.androidtest.nfc.NfcReaderButton
import com.laerodev.androidtest.ui.theme.AndroidTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidTestTheme {
                App()
            }
        }
    }
}

@Composable
fun App() {
//    SetTimeOut()

    var nfcTagId by remember { mutableStateOf("") }

    // Using the Activity Result API for cleaner result handling
    val nfcResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            nfcTagId = data?.getStringExtra("nfc_tag_id") ?: "No ID Found"
        } else if (result.resultCode == Activity.RESULT_CANCELED) {
            nfcTagId = "NFC Scan Cancelled or Failed."
        }
    }

    NfcReaderButton(
        currentNfcId = nfcTagId,
        onLaunchNfcScan = { nfcResultLauncher.launch(Intent(it, NfcActivity::class.java)) }
    )
}

@Composable
fun SetTimeOut() {
    InactivityScreen()
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidTestTheme {

    }
}