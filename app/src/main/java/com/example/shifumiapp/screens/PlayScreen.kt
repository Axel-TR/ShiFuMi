package com.example.shifumiapp.screens

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.shifumiapp.GameViewModel
import com.example.shifumiapp.R
import com.example.shifumiapp.dimitri
import kotlin.math.sqrt
import kotlin.random.Random

@Composable
fun PlayScreen(navController: NavHostController, gameViewModel: GameViewModel) {
    val context = LocalContext.current
    val sensorManager = remember { context.getSystemService(Context.SENSOR_SERVICE) as SensorManager }
    val gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)

    var shakeCount by remember { mutableIntStateOf(0) }
    var result by remember { mutableStateOf("") }

    val sensorListener = remember {
        object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                event?.let {
                    val angularSpeed = sqrt(it.values[0] * it.values[0] + it.values[1] * it.values[1] + it.values[2] * it.values[2])

                    if (angularSpeed > 5) {
                        shakeCount++
                    }

                    if (shakeCount >= 6) {
                        val options = listOf("pierre", "feuille", "ciseau")
                        result = options[Random.nextInt(options.size)]
                        shakeCount = 0
                    }
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }
    }

    DisposableEffect(Unit) {
        sensorManager.registerListener(sensorListener, gyroscope, SensorManager.SENSOR_DELAY_UI)

        onDispose {
            sensorManager.unregisterListener(sensorListener)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFEA00))

    ) {

        Spacer(modifier = Modifier.height(10.dp))

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .width(200.dp)
                .height(75.dp)
                .clip(GenericShape { size, _ ->
                    val width = size.width
                    val height = size.height
                    moveTo(width * 0f, 0f)
                    lineTo(width * 0.9f, 0f)
                    lineTo(width, height * 0.2f)
                    lineTo(width, height * 0.8f)
                    lineTo(width * 0.9f, height)
                    lineTo(width * 0f, height)
                    lineTo(0f, height * 0.8f)
                    lineTo(0f, height * 0.2f)
                    close()
                })
                .background(Color.Black)
                .clickable {
                    navController.navigate("home")
                }
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .width(186.dp)
                    .height(62.dp)
                    .clip(GenericShape { size, _ ->
                        val width = size.width
                        val height = size.height
                        moveTo(width * 0.0f, 0f)
                        lineTo(width * 0.91f, 0f)
                        lineTo(width, height * 0.2f)
                        lineTo(width, height * 0.8f)
                        lineTo(width * 0.91f, height)
                        lineTo(width * 0.0f, height)
                        lineTo(0f, height * 0.8f)
                        lineTo(0f, height * 0.2f)
                        close()
                    })
                    .background(Color.White)
                    .padding(12.dp)
            ) {
                Text(text = "RETOUR",
                    fontSize = 34.sp,
                    color = Color.Black,
                    fontFamily = dimitri,)
            }
        }

        if (result.isNotEmpty()) {

            val imageRes = when (result) {
                "pierre" -> R.drawable.pierre
                "feuille" -> R.drawable.feuille
                "ciseau" -> R.drawable.ciseau
                else -> null
            }

            imageRes?.let {
                Spacer(modifier = Modifier.height(100.dp))
                Image(
                    painter = painterResource(id = it),
                    contentDescription = "Résultat: $result",
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}