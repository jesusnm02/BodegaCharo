package com.principio.mobilebodegacharo.View

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.principio.mobilebodegacharo.ComponentUI.NavigationLower
import com.principio.mobilebodegacharo.ComponentUI.TopBarra
import com.principio.mobilebodegacharo.Navigation.AppNavigationLower
import com.principio.mobilebodegacharo.R
import com.principio.mobilebodegacharo.ui.theme.ColorText
import com.principio.mobilebodegacharo.ui.theme.White

@Composable
fun Welcome() {
    val navcontroller = rememberNavController()
    Scaffold(
        topBar = { TopBarra(titulo = R.string.titulo.toString().uppercase(),
            colorBarra = White,
            colortext = ColorText
        ) },
        bottomBar = { NavigationLower(navcontroller = navcontroller) }
    ) { innerPadding->
        Column(modifier = Modifier
            .padding(innerPadding)
        ) {
            //Pintado de las demas vistas
            AppNavigationLower(navcontroller = navcontroller)
        }
    }
}