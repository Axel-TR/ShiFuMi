package com.example.shifumiapp.screens

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
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


@Composable
fun PlayBotScreen(navController: NavHostController, gameViewModel: GameViewModel) {
    val context = LocalContext.current
    val sensorManager = remember { context.getSystemService(Context.SENSOR_SERVICE) as SensorManager }
    val gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)

    var shakeCount by remember { mutableIntStateOf(0) }
    var resultBot by remember { mutableStateOf("") }
    var resultUser by remember { mutableStateOf("") }
    var played by remember { mutableStateOf(false) }
    var userChoice by remember { mutableStateOf<String?>(null) }


    val sensorListener = remember {
        object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                event?.let {
                    val angularSpeed = sqrt(it.values[0] * it.values[0] + it.values[1] * it.values[1] + it.values[2] * it.values[2])
                    if (angularSpeed > 5) {
                        if ((gameViewModel.strategy && userChoice != null) || !gameViewModel.strategy){
                                shakeCount++
                            }
                    }
                    if (shakeCount >= 6) {
                        if ((gameViewModel.strategy && !played) || (!gameViewModel.strategy )){
                            val options = listOf("pierre", "feuille", "ciseau")
                            resultBot = options.random()
                            resultUser = if (userChoice == null) options.random() else userChoice.toString()
                            shakeCount = 0
                            userChoice = null
                            played = true
                        }

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

    val resultText = if (resultBot.isNotEmpty() && resultUser.isNotEmpty()) {
        val outcome = when {
            resultUser == resultBot -> "ÉGALITÉ"
            (resultUser == "pierre" && resultBot == "ciseau") ||
                    (resultUser == "feuille" && resultBot == "pierre") ||
                    (resultUser == "ciseau" && resultBot == "feuille") -> "VICTOIRE"
            else -> "DÉFAITE"
        }
        outcome
    } else {
        "SECOUEZ !!"
    }

    fun getImageFromResult(result: String): Int? = when (result) {
        "pierre" -> R.drawable.pierre_no_bg
        "feuille" -> R.drawable.feuille_no_bg
        "ciseau" -> R.drawable.ciseau_no_bg
        else -> null
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
                        lineTo(-width *0.05f, height * 0.8f)
                        close()
                    })
                    .background(Color.White)
                    .padding(12.dp)
            ) {
                Text(text = "QUITTER",
                    fontSize = 34.sp,
                    color = Color.Black,
                    fontFamily = dimitri,)
            }
        }


        Spacer(modifier = Modifier.height(100.dp))
        Box(
            modifier = Modifier
                .width(260.dp)
                .height(200.dp)
                .clip(GenericShape { size, _ ->
                    val width = size.width
                    val height = size.height
                    moveTo(width * 0.0f, 0f)
                    lineTo(x = width, y = 0f)
                    lineTo(x = width*0.65f, y = height)
                    lineTo(x = 0.0f, y = height)
                    close()
                })
                .background(Color.Black)
                .padding(bottom = 8.dp, top = 8.dp, end = 12.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(260.dp)
                    .height(200.dp)
                    .clip(GenericShape { size, _ ->
                        val width = size.width
                        val height = size.height
                        moveTo(width * 0.0f, 0f)
                        lineTo(x = width, y = 0f)
                        lineTo(x = width*0.66f, y = height)
                        lineTo(x = 0.0f, y = height)
                        close()
                    })

                    .background(Color.White)

            ){
                Text(text = "BOT",
                    Modifier.offset(x = 45.dp,y=(130.dp)),
                    fontSize = 42.sp,
                    color = Color.Black,
                    fontFamily = dimitri,)
                val botImage = getImageFromResult(resultBot) ?: R.drawable.bot_img
                Image(
                    painter = painterResource(id = botImage),
                    contentDescription = "bot",
                    modifier = Modifier.size(140.dp).offset(x = 20.dp)
                )
            }
        }

        Box(
            modifier = Modifier
                .offset(y = (-75).dp)
                .align(Alignment.End)
                .width(260.dp)
                .height(200.dp)
                .clip(GenericShape { size, _ ->
                    val width = size.width
                    val height = size.height
                    moveTo(width*0.35f, 0f)
                    lineTo(x = width, y = 0f)
                    lineTo(x = width, y = height)
                    lineTo(x = 0f, y = height)
                    close()
                })
                .background(Color.Black)
                .padding(bottom = 8.dp, top = 8.dp, start = 12.dp)
        ){
            Box(
                modifier = Modifier
                    .width(260.dp)
                    .height(200.dp)
                    .clip(GenericShape { size, _ ->
                        val width = size.width
                        val height = size.height
                        moveTo(width*0.34f, 0f)
                        lineTo(x = width, y = 0f)
                        lineTo(x = width, y = height)
                        lineTo(x = 0f, y = height)
                        close()
                    })
                    .background(Color.White)

            ){
                Text(text = "VOUS",
                    Modifier.offset(x = 50.dp,y=(130.dp)),
                    fontSize = 42.sp,
                    color = Color.Black,
                    fontFamily = dimitri,)
                val userImage = getImageFromResult(resultUser) ?: R.drawable.pingu_img
                Image(
                    painter = painterResource(id = userImage),
                    contentDescription = "vous",
                    modifier = Modifier.size(140.dp).offset(x = 80.dp)
                )
            }
        }


        if (gameViewModel.strategy && !played) {

            Row(
                modifier = Modifier.offset(y=-60.dp).width(400.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,

            ) {
                val selectedBorderColor = Color.Black
                val unselectedBorderColor = Color(0x00000000)
                val borderColorPierre = if (userChoice == "pierre") selectedBorderColor else unselectedBorderColor
                val borderColorFeuille = if (userChoice == "feuille") selectedBorderColor else unselectedBorderColor
                val borderColorCiseau = if (userChoice == "ciseau") selectedBorderColor else unselectedBorderColor
                Image(
                    painter = painterResource(id = R.drawable.pierre_no_bg),
                    contentDescription = "Pierre",
                    modifier = Modifier
                        .clickable { userChoice = "pierre" }
                        .size(100.dp)
                        .border(4.dp, borderColorPierre, shape = GenericShape { size, _ -> moveTo(0f, 0f); lineTo(size.width, 0f); lineTo(size.width, size.height); lineTo(0f, size.height); close() })
                )


                Image(
                    painter = painterResource(id = R.drawable.feuille_no_bg),
                    contentDescription = "Feuille",
                    modifier = Modifier
                        .clickable { userChoice = "feuille" }
                        .size(100.dp)
                        .border(4.dp, borderColorFeuille, shape = GenericShape { size, _ -> moveTo(0f, 0f); lineTo(size.width, 0f); lineTo(size.width, size.height); lineTo(0f, size.height); close() })
                )


                Image(
                    painter = painterResource(id = R.drawable.ciseau_no_bg),
                    contentDescription = "Ciseau",
                    modifier = Modifier
                        .clickable { userChoice = "ciseau" }
                        .size(100.dp)
                        .border(4.dp, borderColorCiseau, shape = GenericShape { size, _ -> moveTo(0f, 0f); lineTo(size.width, 0f); lineTo(size.width, size.height); lineTo(0f, size.height); close() })
                )
            }


        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .offset(y = -40.dp)
                .width(400.dp)
                .height(70.dp)
                .background(Color.White)
                .drawBehind {
                    val strokeWidth = 8.dp.toPx()
                    val width = size.width
                    val height = size.height

                    drawLine(
                        color = Color.Black,
                        start = Offset(0f, 0f),
                        end = Offset(width, 0f),
                        strokeWidth = strokeWidth
                    )

                    drawLine(
                        color = Color.Black,
                        start = Offset(0f, height),
                        end = Offset(width, height),
                        strokeWidth = strokeWidth
                    )
                }
        ) {
            Text(
                text = resultText,
                fontSize = 42.sp,
                color = Color.Black,
                fontFamily = dimitri,
            )
        }




    }
}