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
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import kotlin.math.sqrt
import kotlin.random.Random

val dimitri = FontFamily(
    Font(R.font.dimis___)
)

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

@Composable
fun HomeScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFEA00))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "PIERRE\nNEUILLE\nCISEAU",
            fontSize = 48.sp,
            fontFamily = dimitri,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color.Black,
            style = TextStyle(
                lineHeight = 43.sp
            ),
            modifier = Modifier.padding(top = 60.dp)
        )
        Spacer(modifier = Modifier.height(40.dp))
        Image(
            painter = painterResource(id = R.drawable.pierre),
            contentDescription = "Pierre",
            modifier = Modifier.size(124.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Image(
                painter = painterResource(id = R.drawable.feuille),
                contentDescription = "Feuille",
                modifier = Modifier.size(140.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.ciseau),
                contentDescription = "Ciseau",
                modifier = Modifier.size(140.dp)
            )

        }
        Spacer(modifier = Modifier.height(60.dp))

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .width(200.dp)
                .height(75.dp)
                .clip(GenericShape { size, _ ->
                    val width = size.width
                    val height = size.height
                    moveTo(width * 0.1f, 0f)
                    lineTo(width * 0.9f, 0f)
                    lineTo(width, height * 0.2f)
                    lineTo(width, height * 0.8f)
                    lineTo(width * 0.9f, height)
                    lineTo(width * 0.1f, height)
                    lineTo(0f, height * 0.8f)
                    lineTo(0f, height * 0.2f)
                    close()
                })
                .background(Color.Black)
                .clickable {
                    navController.navigate("play")
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
                        moveTo(width * 0.09f, 0f)
                        lineTo(width * 0.91f, 0f)
                        lineTo(width, height * 0.2f)
                        lineTo(width, height * 0.8f)
                        lineTo(width * 0.91f, height)
                        lineTo(width * 0.09f, height)
                        lineTo(0f, height * 0.8f)
                        lineTo(0f, height * 0.2f)
                        close()
                    })
                    .background(Color.White)
                    .padding(12.dp)
            ) {
                Text(text = "JOUER",
                    fontSize = 34.sp,
                    color = Color.Black,
                    fontFamily = dimitri,)
            }
        }
    }
}


