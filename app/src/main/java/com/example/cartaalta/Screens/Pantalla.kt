package com.example.cartaalta.Screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.cartaalta.funciones.Baraja
import com.example.cartaalta.R
import com.example.cartaalta.funciones.Carta
import com.example.cartaalta.funciones.Jugador
import com.example.cartaalta.modelo.Routes

//Imagen de fondo
@Composable
fun Wallpaper() {
    Image(
        painter = painterResource(id = R.drawable.fondo_poker), contentDescription = "",
        modifier = Modifier
            .fillMaxSize(), contentScale = ContentScale.FillBounds
    )
}

@Composable
fun modo2vs2(navController: NavHostController) {
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
}

@Composable
fun modoVsBanca(navController: NavHostController) {
    Row(Modifier.padding(10.dp)) {
        Button(
            onClick = { navController.navigate(Routes.Pantalla3.route) },
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

@Composable
fun ChooseGameMode(navController: NavHostController) {
    Wallpaper()

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            Text(text = "Seleccione modo de juego")
        }
        modo2vs2(navController = navController)
        modoVsBanca(navController = navController)
    }
}

//funcion que nos va imprimiendo las cartas en base a la mano y un contexto
@Composable
fun bucleCarta(mano: MutableList<Int>, context: Context) {
    LazyRow {
        items(mano) { item ->
            val intCarta = context.resources.getIdentifier(
                "c${item}",
                "drawable",
                context.packageName,
            )
            Image(
                painter = painterResource(id = intCarta),
                contentDescription = "",
                modifier = Modifier.size(100.dp)
            )
        }
    }
}

/*Imprimimos y damos formato para dos bucles, uno para cada jugador,
haciendo uso de la funcion de bucles anterior*/
@Composable
fun imprimeBucles(player1: Jugador, player2: Jugador, context: Context) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
    ) {
        bucleCarta(mano = player1.mano, context = context)
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.Top,
    ) {
        bucleCarta(mano = player2.mano, context = context)
    }

}

@Preview
@Composable
fun Juego() {
    Wallpaper()

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

    //Variables necesarias para los jugadores
    var handPlayer1 by remember { mutableStateOf(mutableListOf<Int>()) }
    val player1 = Jugador("player1", handPlayer1)
    var handPlayer2 by remember { mutableStateOf(mutableListOf<Int>()) }
    val player2 = Jugador("player2", handPlayer2)
    var playerTurn1 by remember { mutableStateOf(true) }
    var playerTurn2 by remember { mutableStateOf(false) }
    var puntPlayer1 by remember { mutableStateOf(0) }
    var puntPlayer2 by remember { mutableStateOf(0) }

    imprimeBucles(player1 = player1, player2 = player2, context = context)

    Column(
        Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        dameCarta(onDameCartaClick = {
            val carta = Baraja.dameCarta()
            if (playerTurn1) {
                dorsoCarta = "c${carta.idDrawable}"
                player1.mano.add(carta.idDrawable)
                playerTurn1 = false
                playerTurn2 = true
                /*puntPlayer1 += carta.puntosMin
                if (puntPlayer1 >= 21) println("GANASTE JUGADOR1")*/
                sumarPuntosJugador(puntPlayer1, carta)
                puntPlayer1 += carta.puntosMin //LAMBDA??????


            } else if (playerTurn2) {
                dorsoCarta = "c${carta.idDrawable}"
                player2.mano.add(carta.idDrawable)
                playerTurn1 = true
                playerTurn2 = false
                /*puntPlayer2 += carta.puntosMin
                if (puntPlayer2 >= 21) println("GANASTE JUGADOR2")*/
                sumarPuntosJugador(puntPlayer2, carta)
                puntPlayer2 += carta.puntosMin//LAMBDA??????
            }
        })


        //Metodo que nos permite ir actualizando las cartas
        LaunchedEffect(dorsoCarta) {
            val id = context.resources.getIdentifier(dorsoCarta, "drawable", context.packageName)
            idCarta = id
        }
    }

}

fun sumarPuntosJugador(puntosPlayer:Int, carta: Carta){
    val updatePuntos = puntosPlayer + carta.puntosMin
    if (updatePuntos >= 21) println("GANASTE JUGADOR2") //TIENE QUE SER EL JUGADOR QUE GANA
}

//Funcion lambda que al pulsar en el boton dame carta, obtiene una carta de la baraja
@Composable
fun dameCarta(onDameCartaClick: () -> Unit) {
    Row(Modifier.padding(10.dp)) {
        Button(
            onClick = {
                onDameCartaClick()
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
}