package com.laerodev.androidtest.nfc

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.laerodev.androidtest.ui.theme.AndroidTestTheme

class NfcActivity : ComponentActivity() {
    private val TAG = "NfcActivity"
    private var nfcAdapter: NfcAdapter? = null
    private lateinit var pendingIntent: PendingIntent

    private var nfcIdState by mutableStateOf("Waiting for NFC Tag...")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        nfcAdapter = NfcAdapter.getDefaultAdapter(this)

        setContent {
            AndroidTestTheme { // Use your app's theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NfcScanScreen(nfcId = nfcIdState)
                }
            }
        }
    }

    @SuppressLint("UnsafeIntentLaunch")
    override fun onResume() {
        super.onResume()

        if (nfcAdapter!!.isEnabled) {
            intent = Intent(this, NfcActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)

//            val intentFilter = IntentFilter()
//            val techLists: String[][] = arrayOf(arrayOf(""))

            nfcAdapter!!.enableForegroundDispatch(this,
                PendingIntent.getActivity(
                    this, 0, intent, PendingIntent.FLAG_MUTABLE)
                , null, null)

        }
    }


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (intent.action == NfcAdapter.ACTION_TAG_DISCOVERED ||
            intent.action == NfcAdapter.ACTION_NDEF_DISCOVERED ||
            intent.action == NfcAdapter.ACTION_TECH_DISCOVERED
        ) {
            val tag: Tag? = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG, Tag::class.java)

            tag?.id?.let { idBytes ->
                val nfcTagId = bytesToHex(idBytes)
                nfcIdState = "Tag ID: $nfcTagId"
                val resultIntent = Intent().apply {
                    putExtra("nfc_tag_id", nfcTagId)
                }
                setResult(RESULT_OK, resultIntent)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        if (nfcAdapter?.isEnabled == true) {
            nfcAdapter?.disableForegroundDispatch(this)
        }
    }

    private fun bytesToHex(bytes: ByteArray): String {
        val sb = StringBuilder()
        for (b in bytes) {
            sb.append(String.format("%02X", b))
        }
        return sb.toString()
    }
}

@Composable
fun NfcScanScreen(nfcId: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "NFC Scanner", style = MaterialTheme.typography.headlineMedium)
        Text(
            text = nfcId,
            modifier = Modifier.padding(top = 16.dp),
            style = MaterialTheme.typography.bodyLarge
        )
        if (nfcId == "Waiting for NFC Tag...") {
            CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
        }
    }
}
