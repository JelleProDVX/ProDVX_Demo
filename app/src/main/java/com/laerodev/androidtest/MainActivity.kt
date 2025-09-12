package com.laerodev.androidtest

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.CheckBox
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.laerodev.androidtest.adaptive_light.AdaptiveLightActivity
import com.laerodev.androidtest.inactivity.InactivityScreen
import com.laerodev.androidtest.led.LedActivity
import com.laerodev.androidtest.nfc.NfcActivity
import com.laerodev.androidtest.test.TestActivity
import com.laerodev.androidtest.ui.theme.AndroidTestTheme
import kotlin.jvm.java

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidTestTheme {
                var dev by remember{mutableStateOf(false)}

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text("DevMode")
                    Checkbox(
                        checked = dev,
                        onCheckedChange = { dev = it }
                    )
                }

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(onClick = {
                        val intent = Intent(this@MainActivity, AdaptiveLightActivity::class.java)
                        startActivity(intent)
                    }) {
                        Text("Adaptive Lighting")
                    }

                    Button(onClick = {
                        val intent = Intent(this@MainActivity, NfcActivity::class.java)
                        startActivity(intent)
                    }) {
                        Text("NFC")
                    }

                    Button(onClick = {
                        val intent = Intent(this@MainActivity, LedActivity::class.java)
                        startActivity(intent)
                    }) {
                        Text("Led Control")
                    }

                    if(dev) {
                        Button(onClick = {
                            val intent = Intent(this@MainActivity, TestActivity::class.java)
                            startActivity(intent)
                        }) {
                            Text("Test")
                        }
                        Button(onClick = {
                            val intent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                            startActivity(intent)
                        }){Text("ConnectionSettings")}

                    }



                }
            }
        }
    }
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