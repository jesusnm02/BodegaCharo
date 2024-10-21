package com.principio.mobilebodegacharo.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.principio.mobilebodegacharo.View.PaginaAyuda
import com.principio.mobilebodegacharo.View.PaginaCategoria
import com.principio.mobilebodegacharo.View.PaginaInicio
import com.principio.mobilebodegacharo.View.PaginaPago
import com.principio.mobilebodegacharo.View.PaginaPedido

@Composable
fun AppNavigationLower(navcontroller: NavHostController) {
    NavHost(navController = navcontroller,
        startDestination = ElementsNav.Inicio.routes) {
        composable(route = ElementsNav.Inicio.routes) {
            //UI Inicio
            PaginaInicio()
        }

        composable(route = ElementsNav.Pedido.routes) {
            //UI Pedido
            PaginaPedido()
        }

        composable(route = ElementsNav.Pago.routes) {
            //UI Pago
            PaginaPago()
        }

        composable(route = ElementsNav.Ayuda.routes) {
            //UI Ayuda
            PaginaAyuda()
        }

        composable(route = ElementsNav.Categoria.routes) {
            //UI Categoria
            PaginaCategoria()
        }
    }
}