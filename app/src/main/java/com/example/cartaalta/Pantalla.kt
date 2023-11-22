package com.example.cartaalta

import android.content.Context
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
import androidx.compose.foundation.layout.mandatorySystemGesturesPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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

    //Imagen de fondo
    Image(
        painter = painterResource(id = R.drawable.fondo_poker), contentDescription = "",
        modifier = Modifier
            .fillMaxSize()
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
                    text = "2 jugadores",
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
                    text = "Contra maquina",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
        }
    }
}
@Composable
fun bucleCarta(mano: MutableList<Int>, context: Context){
    LazyRow {
        items(mano) { item ->
            var idCarta = context.resources.getIdentifier(
                "c${item}",
                "drawable",
                context.packageName,
            )
            Image(
                painter = painterResource(id = idCarta),
                contentDescription = "",
                modifier = Modifier.size(100.dp)
            )
        }
    }
}

@Preview
@Composable
fun Juego() {
    //Variables necesarias para los jugadore
    var mano by remember { mutableStateOf(mutableListOf<Int>()) }
    val jugador1 = Jugador("jugador1", mano)

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
        bucleCarta(mano = mano, context = context)

        /*LazyRow {
            items(mano) { item ->
                idCarta = context.resources.getIdentifier(
                    "c${item}",
                    "drawable",
                    context.packageName,
                )
                Image(
                    painter = painterResource(id = idCarta),
                    contentDescription = "",
                    modifier = Modifier.size(100.dp)
                )
            }
        }*/

        Row(Modifier.padding(10.dp)) {
            Button(
                onClick = {
                    val carta = Baraja.dameCarta()
                    dorsoCarta = "c${carta.idDrawable}"
                    mano.add(carta.idDrawable)
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