package com.example.cartaalta

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController

//@Preview
@Composable
fun modoJuego(navController: NavHostController) {
    //Variables necesarias para nuestro juego
    val context = LocalContext.current
    var dorsoCarta by rememberSaveable { mutableStateOf("detras") }
    var idCarta by rememberSaveable {
        mutableStateOf(
            context.resources.getIdentifier(
                dorsoCarta,
                "drawable",
                context.packageName
            )
        )
    }
    //Imagen de fondo
    Image(
        painter = painterResource(id = R.drawable.fondo_poker), contentDescription = "",
        modifier = Modifier
            .fillMaxSize()
            .paint(painterResource(id = idCarta))
    )

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            Text(text = "Seleccione modo de juego")
        }
        Row(Modifier.padding(10.dp)) {
            Button(
                onClick = { navController.navigate(Routes.Pantalla2.route) },
                Modifier
                    .padding(10.dp)
                    .border(2.dp, color = Color.Red, shape = CircleShape),
                colors = ButtonDefaults.textButtonColors(Color.White)
            ) {
                Text(
                    text = "Contra crupier",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
        }
        Row(Modifier.padding(10.dp)) {
            Button(
                onClick = {},
                Modifier
                    .padding(10.dp)
                    .border(2.dp, color = Color.Red, shape = CircleShape),
                colors = ButtonDefaults.textButtonColors(Color.White)
            ) {
                Text(
                    text = "2 jugadores",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
        }
    }
}

@Preview
@Composable
fun Juego() {
    //Variables necesarias para nuestro juego
    val context = LocalContext.current
    var dorsoCarta by rememberSaveable { mutableStateOf("detras") }
    var idCarta by rememberSaveable {
        mutableStateOf(
            context.resources.getIdentifier(
                dorsoCarta,
                "drawable",
                context.packageName
            )
        )
    }
    //Imagen de fondo
    Image(
        painter = painterResource(id = R.drawable.fondo_poker), contentDescription = "",
        modifier = Modifier
            .fillMaxSize()
            .paint(painterResource(id = idCarta))
    )

    Baraja.crearBaraja()
    var jugador1 = Jugador("jugador", listOf())
    var crupier = Jugador("crupier", listOf())

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            Image(
                painter = painterResource(id = idCarta),
                contentDescription = "",
                Modifier.size(120.dp)
            )
            Image(
                painter = painterResource(id = idCarta),
                contentDescription = "",
                Modifier.size(120.dp)
            )
        }

        Row(Modifier.padding(10.dp)) {
            Button(
                onClick = {
                    //Si se acaba nuestra baraja, vuelve a crear una nueva (para que no cierre el programa)
                    Baraja.barajar()
                    if (Baraja.listaCartas.size == 0) {
                        Baraja.crearBaraja()
                        dorsoCarta = "detras"
                    }
                    val carta = Baraja.dameCarta()
                    dorsoCarta = "c${carta.idDrawable}"
                    println("$carta || $dorsoCarta || ${Baraja.listaCartas.size}")

                },
                Modifier
                    .padding(10.dp)
                    .border(2.dp, color = Color.Red, shape = CircleShape),
                colors = ButtonDefaults.textButtonColors(Color.White)
            ) {
                Text(
                    text = "Dame carta",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
        }
        //Metodo que nos permite ir actualizando las cartas
        LaunchedEffect(dorsoCarta) {
            val id = context.resources.getIdentifier(dorsoCarta, "drawable", context.packageName)
            idCarta = id
        }
    }
}