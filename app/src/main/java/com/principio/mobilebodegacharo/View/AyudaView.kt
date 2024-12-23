package com.principio.mobilebodegacharo.View

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.principio.mobilebodegacharo.ComponentUI.mostrarOpciones

val TITULODEPAGINAAYUDA: String = "Ayuda"
@Composable
fun PaginaAyuda() {
    mostrarOpciones(textopagina =  TITULODEPAGINAAYUDA.toString().uppercase())
}

@Preview(showSystemUi = true)
@Composable
fun show1() {
    PaginaAyuda()
}