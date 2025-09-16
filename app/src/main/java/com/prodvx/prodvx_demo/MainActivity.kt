package com.prodvx.prodvx_demo

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import com.prodvx.prodvx_demo.adaptive_light.AdaptiveLightActivity
import com.prodvx.prodvx_demo.led.LedActivity
import com.prodvx.prodvx_demo.nfc.NfcActivity
import com.prodvx.prodvx_demo.test.TestActivity
import com.prodvx.prodvx_demo.ui.theme.AndroidTestTheme
import kotlin.jvm.java

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidTestTheme {
                var dev by remember{mutableStateOf(false)}

//                Row(
//                    verticalAlignment = Alignment.CenterVertically,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(16.dp)
//                ) {
//                    Text("DevMode")
//                    Checkbox(
//                        checked = dev,
//                        onCheckedChange = { dev = it }
//                    )
//                }

                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,

                ) {
                    val adaptiveLightIntent = Intent(this@MainActivity, AdaptiveLightActivity::class.java)
                    val nfcIntent = Intent(this@MainActivity, NfcActivity::class.java)
                    val ledIntent = Intent(this@MainActivity, LedActivity::class.java)

                    val testIntent = Intent(this@MainActivity, TestActivity::class.java)

                    ActivityLauncher(this@MainActivity, adaptiveLightIntent, "Adaptive Lighting")
                    ActivityLauncher(this@MainActivity, nfcIntent, "NFC")
                    ActivityLauncher(this@MainActivity, ledIntent, "LED Demo")

                    if(dev) {
                        ActivityLauncher(this@MainActivity, testIntent, "Test")
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
fun ActivityLauncher(ctx: Context, int: Intent, text: String) {
    Button(
        modifier = Modifier
            .padding(16.dp),
        onClick = {
            ctx.startActivity(int)
        }
    ){
        Text(text)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidTestTheme {

    }
}