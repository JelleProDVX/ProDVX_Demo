package com.laerodev.androidtest.led

import android.R.attr.onClick
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.laerodev.androidtest.LRGB
import com.laerodev.androidtest.api.sendRequest
import com.laerodev.androidtest.ui.theme.AndroidTestTheme
import io.ktor.http.HttpMethod
import io.ktor.util.collections.getValue
import io.ktor.util.collections.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LedActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent{
            AndroidTestTheme {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    var text by remember { mutableStateOf("") }
                    Button (
                        onClick = {
                            changeLedColorSdk(applicationContext, 0, 0x01FF0000)
                            text = "Using SDK: Intent with action.CHANGE_LED_COLOR, and Extras 'color', 0x01FF0000"
                        }) {
                            Text("Set Leds Red using SDK")
                        }
                    Button (
                        onClick = {
                            changeLedColorSdk(applicationContext, 0, 0x0100FF00)
                            text = "Using SDK: Intent with action.CHANGE_LED_COLOR, and Extras 'color', 0x0100FF00"
                        }
                    ) {
                        Text("Set Leds Green using SDK")
                    }
                    Button (
                        onClick = {
                            changeLedColorApi(LRGB(255, 0, 0, 255))
                            text = "Using API: URL = http://localhost:3535/setAllLeds?lrgb=255,0,0,255"
                        }
                    ) {
                        Text("Set Leds Blue using API")
                    }
                    Text(text)
                    Button(onClick = {
                        val intent = Intent(this@LedActivity, SSeriesLedDemoActivity::class.java)
                        startActivity(intent)
                    }) {
                        Text("S-Series Led Demo")
                    }
                }
            }
        }
    }
}

fun changeLedColorSdk(context: Context, colordemo: Int, color: Int?) {
    println("ChangeLedColorSdk with colordemo: ${colordemo} and color: $color")
    val intent = Intent("action.CHANGE_LED_COLOR")

    if(colordemo < 0 || colordemo > 5) {
        //Invalid value
        return
    }

    if(colordemo > 0) {
        intent.putExtra("colordemo", colordemo)
    }

    if(color != null && color > 0) {
        intent.putExtra("color", color)
    }

    context.sendBroadcast(intent)
}

fun changeLedColorApi(lrgb: LRGB) {
    println("ChangeLedColorApi with color: ${lrgb},")
    CoroutineScope(Dispatchers.IO).launch {
        sendRequest(HttpMethod.Get, "/setAllLeds", mapOf("lrgb" to "${lrgb.L},${lrgb.R},${lrgb.G},${lrgb.B}"))
    }
}