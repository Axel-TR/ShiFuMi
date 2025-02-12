package com.example.shifumiapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shifumiapp.ui.theme.ShiFuMiAppTheme
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import kotlin.math.sqrt
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShiFuMiAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavigation(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}


@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = modifier
    ) {
        composable("home") { HomeScreen(navController) }
        composable("play") { PlayScreen(navController) }
    }
}
@Composable
fun PlayScreen(navController: NavHostController) {
    val context = LocalContext.current
    val sensorManager = remember { context.getSystemService(Context.SENSOR_SERVICE) as SensorManager }
    val gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)

    var shakeCount by remember { mutableStateOf(0) }
    var result by remember { mutableStateOf("") }

    val sensorListener = remember {
        object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                event?.let {
                    val angularSpeed = sqrt(it.values[0] * it.values[0] + it.values[1] * it.values[1] + it.values[2] * it.values[2])

                    if (angularSpeed > 5) {
                        shakeCount++
                    }

                    if (shakeCount >= 3) {
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
            .padding(16.dp)
    ) {
        Text(
            text = "Retour",
            modifier = Modifier
                .clickable { navController.navigate("home") }
                .padding(8.dp)
        )
        Text(text = "Faites 3 secousses pour jouer !")
        if (result.isNotEmpty()) {
            Text(text = "Résultat: $result")
            val imageRes = when (result) {
                "pierre" -> R.drawable.pierre
                "feuille" -> R.drawable.feuille
                "ciseau" -> R.drawable.ciseau
                else -> null
            }

            imageRes?.let {
                Image(
                    painter = painterResource(id = it),
                    contentDescription = "Résultat: $result",
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}


@Composable
fun HomeScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Bienvenue",
            modifier = Modifier
                .padding(8.dp)
        )
        Text(
            text = "Jouer",
            modifier = Modifier
                .clickable{navController.navigate("play")}
        )
    }
}


