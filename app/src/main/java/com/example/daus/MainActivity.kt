// fet per Yago Alonso i Alex Vilanova
package com.example.rollthedice

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    RollTheDiceApp()
                }
            }
        }
    }
}

@Composable
fun RollTheDiceApp() {
    var dice1 by remember { mutableStateOf(1) }
    var dice2 by remember { mutableStateOf(1) }
    var credits by remember { mutableStateOf(10) }
    val context = LocalContext.current

    fun updateCredits(newCredits: Int) {
        credits = newCredits
    }

    fun checkDiceResults(dice1: Int, dice2: Int, currentCredits: Int) {
        when {
            dice1 == 6 && dice2 == 6 -> {
                Toast.makeText(context, "¡DOBLE 6! +10 CRÉDITOS", Toast.LENGTH_SHORT).show()
                updateCredits(currentCredits + 10)
            }
            dice1 == dice2 -> {
                Toast.makeText(context, "¡DADOS IGUALES! +5 CRÉDITOS", Toast.LENGTH_SHORT).show()
                updateCredits(currentCredits + 5)
            }
        }
    }

    fun rollDices() {
        if (credits >= 2) {
            dice1 = Random.nextInt(1, 7)
            dice2 = Random.nextInt(1, 7)
            credits -= 2
            checkDiceResults(dice1, dice2, credits)
        } else {
            Toast.makeText(context, "No tienes créditos suficientes", Toast.LENGTH_SHORT).show()
        }
    }

    fun rollSingleDice(diceNumber: Int) {
        if (credits >= 1) {
            when (diceNumber) {
                1 -> dice1 = Random.nextInt(1, 7)
                2 -> dice2 = Random.nextInt(1, 7)
            }
            credits -= 1
            checkDiceResults(dice1, dice2, credits)
        } else {
            Toast.makeText(context, "No tienes créditos suficientes", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            elevation = 4.dp,
            modifier = Modifier.padding(8.dp)
        ) {
            Row(
                modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Star,
                    contentDescription = "Créditos",
                    tint = Color(0xFFFFD700)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "Créditos: $credits",
                    style = MaterialTheme.typography.h6
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            DiceImage(
                value = dice1,
                onClick = { rollSingleDice(1) }
            )
            DiceImage(
                value = dice2,
                onClick = { rollSingleDice(2) }
            )
        }

        Button(
            onClick = { rollDices() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Tirar Dados (2 créditos)")
        }

        OutlinedButton(
            onClick = {
                dice1 = 1
                dice2 = 1
                credits = 10
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Reiniciar Juego")
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            Image(
                painter = painterResource(id = R.drawable.lsg),
                contentDescription = "Logo La Salle Gràcia",
                modifier = Modifier.size(50.dp),
                contentScale = ContentScale.Fit
            )
        }
    }
}

@Composable
fun DiceImage(
    value: Int,
    onClick: () -> Unit
) {
    Card(
        elevation = 4.dp,
        modifier = Modifier
            .padding(8.dp)
            .clickable(onClick = onClick)
    ) {
        Image(
            painter = painterResource(
                when (value) {
                    1 -> R.drawable.dice1
                    2 -> R.drawable.dice2
                    3 -> R.drawable.dice3
                    4 -> R.drawable.dice4
                    5 -> R.drawable.dice5
                    6 -> R.drawable.dice6
                    else -> R.drawable.dice1
                }
            ),
            contentDescription = "Dado $value",
            modifier = Modifier.size(100.dp),
            contentScale = ContentScale.Fit
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MaterialTheme {
        RollTheDiceApp()
    }
}