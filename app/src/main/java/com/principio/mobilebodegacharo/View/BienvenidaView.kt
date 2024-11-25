package com.principio.mobilebodegacharo.View

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.principio.mobilebodegacharo.ComponentUI.NavigationLower
import com.principio.mobilebodegacharo.ComponentUI.TopBarra
import com.principio.mobilebodegacharo.Controller.PedidosController
import com.principio.mobilebodegacharo.Navigation.AppNavigationLower
import com.principio.mobilebodegacharo.ui.theme.ColorText
import com.principio.mobilebodegacharo.ui.theme.White

@Composable
fun Welcome() {
    val navcontroller = rememberNavController()
    val pedidosController = remember { PedidosController() } // Instancia persistente

    Scaffold(
        topBar = {
            val precioTotal by pedidosController.precioTotal.collectAsState()
            TopBarra(
                titulo = "Bodega Charo",
                colorBarra = White,
                colortext = ColorText,
                precioTotal = "%.2f".format(precioTotal),
                navController = navcontroller
            )
        },
        bottomBar = { NavigationLower(navcontroller = navcontroller) }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            AppNavigationLower(
                navcontroller = navcontroller,
                pedidosController = pedidosController // Pasar el controlador a todas las pantallas
            )
        }
    }
}