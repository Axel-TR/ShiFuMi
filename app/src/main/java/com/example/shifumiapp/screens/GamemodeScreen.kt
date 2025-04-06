package com.example.shifumiapp.screens

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
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.shifumiapp.GameViewModel
import com.example.shifumiapp.dimitri



@Composable
fun GamemodeScreen(navController: NavHostController, gameViewModel : GameViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFEA00))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "GAME\nMODE",
            fontSize = 56.sp,
            fontFamily = dimitri,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color.Black,
            style = TextStyle(
                lineHeight = 56.sp
            ),
            modifier = Modifier.padding(top = 60.dp)
        )
        Spacer(modifier = Modifier.height(40.dp))
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(vertical= 40.dp)
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
                    navController.navigate("playSolo")
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
                Text(
                    text = "SOLO",
                    fontSize = 34.sp,
                    color = Color.Black,
                    fontFamily = dimitri,
                )
            }
        }
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
                    navController.navigate("playBot")
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
                Text(
                    text = "VS ORDI",
                    fontSize = 34.sp,
                    color = Color.Black,
                    fontFamily = dimitri,
                )
            }
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(vertical = 40.dp)
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
                Text(
                    text = "RETOUR",
                    fontSize = 34.sp,
                    color = Color.Black,
                    fontFamily = dimitri,
                )
            }
        }
        Button(
            onClick = {
                gameViewModel.strategy = !gameViewModel.strategy // Change l'état de la stratégie
            },
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Text(
                text = if (gameViewModel.strategy) "Désactiver la Stratégie" else "Activer la Stratégie",
                fontSize = 18.sp,
                color = Color.Black
            )
        }

    }
}