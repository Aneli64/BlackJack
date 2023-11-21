package com.example.cartaalta

/**
 * Rutas que usaremos para controlar los distintos modos de juego de nuestra app
 * @param route ruta a la que nos dirigimos
 */
sealed class Routes(val route: String) {
    object Pantalla1 : Routes("pantalla1")
    object Pantalla2 : Routes("pantalla2")
}