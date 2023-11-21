package com.example.cartaalta
class Jugador (var nombre: String, var mano: List<Carta>){ //faltaria añadir fichas

    fun rellenaMano(){
        mano = listOf(Baraja.dameCarta(), Baraja.dameCarta())
    }

    fun pedirCartaCond(){

    }
}